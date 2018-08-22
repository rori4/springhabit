package org.rangelstoilov.services.habit;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.entities.Habit;
import org.rangelstoilov.entities.HabitStats;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.habit.HabitModel;
import org.rangelstoilov.repositories.HabitRepository;
import org.rangelstoilov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public HabitServiceImpl(HabitRepository habitRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public HabitModel add(HabitModel habitModel, String userEmail) {
        User user = this.userRepository.findFirstByEmail(userEmail);
        Habit habit = this.modelMapper.map(habitModel, Habit.class);
        habit.setUser(user);
        habit.setOrderNumber(this.habitRepository.countAllByUserAndStatus(user, Status.ACTIVE)+ 1);
        return this.modelMapper.map(this.habitRepository.save(habit), HabitModel.class);
    }

    @Override
    public boolean markPlus(String id, String userEmail) {
        Habit habitById = this.habitRepository.findHabitById(id);
        User user = this.userRepository.findFirstByEmail(userEmail);
        return performCheckAndAddStats(habitById, user, 1);
    }

    @Override
    public boolean markMinus(String id, String userEmail) {
        Habit habitById = this.habitRepository.findHabitById(id);
        User user = this.userRepository.findFirstByEmail(userEmail);
        return performCheckAndAddStats(habitById, user, -1);
    }

    @Override
    public boolean archive(String id, String userEmail) {
        Habit habitById = this.habitRepository.findHabitById(id);
        User user = this.userRepository.findFirstByEmail(userEmail);
        if (habitById.getUser().getId().equals(user.getId())) {
            Integer orderNumber = habitById.getOrderNumber();
            fixOrderOfElements(orderNumber);
            habitById.setOrderNumber(this.habitRepository.countAllByUserAndStatus(user, Status.DONE) + 1);
            habitById.setStatus(Status.ARCHIVED);
            this.habitRepository.save(habitById);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<HabitModel> getAllHabits(Status status, String userEmail) {
        User firstByEmail = userRepository.findFirstByEmail(userEmail);
        List<Habit> userToDos = this.habitRepository.findAllByStatusAndUser(status,firstByEmail);
        java.lang.reflect.Type targetListType = new TypeToken<List<HabitModel>>() {}.getType();
        return this.modelMapper.map(userToDos, targetListType);
    }

    private void fixOrderOfElements(Integer orderNumber) {
        List<Habit> allByOrderNumberGreaterThan = this.habitRepository.findAllByOrderNumberGreaterThan(orderNumber);
        if (!allByOrderNumberGreaterThan.isEmpty()) {
            for (Habit element : allByOrderNumberGreaterThan) {
                element.setOrderNumber(element.getOrderNumber() - 1);
            }
        }
    }

    private boolean performCheckAndAddStats(Habit habitById, User user, Integer result) {
        if (habitById.getUser().getId().equals(user.getId())) {
            HabitStats habitStats = new HabitStats(habitById,result);
            List<HabitStats> prevStats = habitById.getHabitStats();
            if (prevStats.add(habitStats)) {
                habitById.setHabitStats(prevStats);
                this.habitRepository.save(habitById);
                return true;
            }
        }
        return false;
    }

}
