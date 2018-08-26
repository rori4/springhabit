package org.rangelstoilov.services.habit;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.entities.Habit;
import org.rangelstoilov.entities.HabitStats;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.habit.HabitModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.repositories.HabitRepository;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public HabitServiceImpl(HabitRepository habitRepository, ModelMapper modelMapper, UserService userService) {
        this.habitRepository = habitRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public HabitModel findById(String id) {
        return this.modelMapper.map(this.habitRepository.findHabitById(id), HabitModel.class);
    }

    @Override
    public HabitModel add(HabitModel habitModel, String userEmail) {
        User user = this.userService.findFirstByEmail(userEmail);
        Habit habit = this.modelMapper.map(habitModel, Habit.class);
        habit.setUser(user);
        habit.setOrderNumber(this.habitRepository.countAllByUserAndTaskStatus(user, TaskStatus.ACTIVE)+ 1);
        return this.modelMapper.map(this.habitRepository.save(habit), HabitModel.class);
    }

    @Override
    public UserRewardModel markPlus(String id, String userEmail) {
        Habit habitById = this.habitRepository.findHabitById(id);
        User user =this.userService.findFirstByEmail(userEmail);
        if(performCheckAndAddStats(habitById, user, 1)){
            return this.userService.rewardUserForTaskDone(userEmail, 1);
        } else
            return null;
    }

    @Override
    public UserRewardModel markMinus(String id, String userEmail) {
        Habit habitById = this.habitRepository.findHabitById(id);
        User user = this.userService.findFirstByEmail(userEmail);
        if(performCheckAndAddStats(habitById, user, -1)){
            return this.userService.damageUserForTaskNotDone(userEmail, 1);
        } else
            return null;
    }

    @Override
    public HabitModel archive(String id, String userEmail) {
        Habit habitById = this.habitRepository.findHabitById(id);
        return changeHabitStatus(userEmail, habitById, TaskStatus.ARCHIVED);
    }

    @Override
    public HabitModel delete(String id, String userEmail) {
        Habit habitById = this.habitRepository.findHabitById(id);
        return changeHabitStatus(userEmail, habitById, TaskStatus.DELETED);
    }

    @Override
    public HabitModel activate(String id, String userEmail) {
        Habit habitById = this.habitRepository.findHabitById(id);
        return changeHabitStatus(userEmail, habitById, TaskStatus.ACTIVE);
    }

    @Override
    public List<HabitModel> getAllHabitsOrdered(TaskStatus taskStatus, String userEmail) {
        User firstByEmail = this.userService.findFirstByEmail(userEmail);
        List<Habit> habits = this.habitRepository.findAllByTaskStatusAndUser(taskStatus,firstByEmail);
        habits.sort(Comparator.comparing(Habit::getOrderNumber));
        java.lang.reflect.Type targetListType = new TypeToken<List<HabitModel>>() {}.getType();
        return this.modelMapper.map(habits, targetListType);
    }

    private HabitModel changeHabitStatus(String userEmail, Habit habitById, TaskStatus taskStatus) {
        User user = this.userService.findFirstByEmail(userEmail);
        if (habitById.getUser().getId().equals(user.getId())) {
            Integer orderNumber = habitById.getOrderNumber();
            TaskStatus habitByIdTaskStatus = habitById.getTaskStatus();
            habitById.setOrderNumber(this.habitRepository.countAllByUserAndTaskStatus(user, taskStatus) + 1);
            habitById.setTaskStatus(taskStatus);
            fixOrderOfElements(habitByIdTaskStatus, orderNumber);
            this.habitRepository.save(habitById);
            return this.modelMapper.map(habitById, HabitModel.class);
        } else {
            return null;
        }
    }

    private void fixOrderOfElements(TaskStatus taskStatus, Integer orderNumber) {
        List<Habit> allByOrderNumberGreaterThan = this.habitRepository.findAllByTaskStatusAndOrderNumberGreaterThan(taskStatus,orderNumber);
        if (!allByOrderNumberGreaterThan.isEmpty()) {
            for (Habit element : allByOrderNumberGreaterThan) {
                element.setOrderNumber(element.getOrderNumber() - 1);
            }
        }
        this.habitRepository.saveAll(allByOrderNumberGreaterThan);
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
