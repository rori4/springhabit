package org.rangelstoilov.services.recurringTask;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.entities.RecurringTask;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.recurringTask.RecurringTaskModel;
import org.rangelstoilov.repositories.RecurringTaskRepository;
import org.rangelstoilov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RecurringTaskServiceImpl implements RecurringTaskService {
    private final RecurringTaskRepository recurringTaskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public RecurringTaskServiceImpl(RecurringTaskRepository recurringTaskRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.recurringTaskRepository = recurringTaskRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public RecurringTaskModel add(RecurringTaskModel recurringTaskModel, String userEmail) {
        User user = this.userRepository.findFirstByEmail(userEmail);
        RecurringTask recurringTask = this.modelMapper.map(recurringTaskModel, RecurringTask.class);
        recurringTask.setUser(user);
        recurringTask.setOrderNumber(this.recurringTaskRepository.countAllByUserAndStatus(user, Status.ACTIVE)+ 1);
        return this.modelMapper.map(this.recurringTaskRepository.save(recurringTask), RecurringTaskModel.class);
    }

    @Override
    public List<RecurringTaskModel> getAllRecurringTasks(Status status, String userEmail) {
        User firstByEmail = userRepository.findFirstByEmail(userEmail);
        List<RecurringTask> userToDos = this.recurringTaskRepository.findAllByStatusAndUser(status,firstByEmail);
        java.lang.reflect.Type targetListType = new TypeToken<List<RecurringTaskModel>>() {}.getType();
        return this.modelMapper.map(userToDos, targetListType);
    }

    @Override
    public boolean markDone(String id, String userEmail) {
        RecurringTask recurringTaskById = this.recurringTaskRepository.findRecurringTaskById(id);
        User user = this.userRepository.findFirstByEmail(userEmail);
        if(recurringTaskById.getUser().getId().equals(user.getId())){
            Integer orderNumber = recurringTaskById.getOrderNumber();
            fixOrderOfElements(orderNumber);
            recurringTaskById.setOrderNumber(this.recurringTaskRepository.countAllByUserAndStatus(user,Status.DONE)+1);
            recurringTaskById.setStatus(Status.DONE);
            recurringTaskById.setCount(recurringTaskById.getCount()+1);
            this.recurringTaskRepository.save(recurringTaskById);
            //TODO: handle user level up, experience and rewards and fights
            return true;
        } else {
            return false;
        }
    }

    private void fixOrderOfElements(Integer orderNumber) {
        List<RecurringTask> allByOrderNumberGreaterThan = this.recurringTaskRepository.findAllByOrderNumberGreaterThan(orderNumber);
        if (!allByOrderNumberGreaterThan.isEmpty()){
            for (RecurringTask element : allByOrderNumberGreaterThan) {
                element.setOrderNumber(element.getOrderNumber()-1);
            }
        }
    }
}
