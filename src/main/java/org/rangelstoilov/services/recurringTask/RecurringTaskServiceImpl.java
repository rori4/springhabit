package org.rangelstoilov.services.recurringTask;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.entities.RecurringTask;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.recurringTask.RecurringTaskModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.repositories.RecurringTaskRepository;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RecurringTaskServiceImpl implements RecurringTaskService {
    private final RecurringTaskRepository recurringTaskRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public RecurringTaskServiceImpl(RecurringTaskRepository recurringTaskRepository, ModelMapper modelMapper, UserService userService) {
        this.recurringTaskRepository = recurringTaskRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public RecurringTaskModel add(RecurringTaskModel recurringTaskModel, String userEmail) {
        User user = this.userService.findFirstByEmail(userEmail);
        RecurringTask recurringTask = this.modelMapper.map(recurringTaskModel, RecurringTask.class);
        recurringTask.setUser(user);
        recurringTask.setOrderNumber(this.recurringTaskRepository.countAllByUserAndStatus(user, Status.ACTIVE)+ 1);
        return this.modelMapper.map(this.recurringTaskRepository.save(recurringTask), RecurringTaskModel.class);
    }

    @Override
    public List<RecurringTaskModel> getAllRecurringTasks(Status status, String userEmail) {
        User firstByEmail = this.userService.findFirstByEmail(userEmail);
        List<RecurringTask> userToDos = this.recurringTaskRepository.findAllByStatusAndUser(status,firstByEmail);
        java.lang.reflect.Type targetListType = new TypeToken<List<RecurringTaskModel>>() {}.getType();
        return this.modelMapper.map(userToDos, targetListType);
    }

    @Override
    public UserRewardModel markDone(String id, String userEmail) {
        RecurringTask recurringTaskById = this.recurringTaskRepository.findRecurringTaskById(id);
        User user = this.userService.findFirstByEmail(userEmail);
        if(recurringTaskById.getUser().getId().equals(user.getId())){
            recurringTaskById.setOrderNumber(this.recurringTaskRepository.countAllByUserAndStatus(user,Status.DONE)+1);
            recurringTaskById.setStatus(Status.DONE);
            fixOrderOfElements(Status.ACTIVE, recurringTaskById.getOrderNumber());
            recurringTaskById.setCount(recurringTaskById.getCount()+1);
            this.recurringTaskRepository.save(recurringTaskById);
            UserRewardModel userRewardModel = this.userService.rewardUserForTaskDone(userEmail, 1);
            return userRewardModel;
        } else {
            return null;
        }
    }

    @Override
    public RecurringTaskModel findById(String id) {
        return this.modelMapper.map(this.recurringTaskRepository.findRecurringTaskById(id),RecurringTaskModel.class);
    }

    @Override
    public RecurringTaskModel archive(String id, String userEmail) {
        RecurringTask recurringTaskById = this.recurringTaskRepository.findRecurringTaskById(id);
        User user = this.userService.findFirstByEmail(userEmail);
        if(recurringTaskById.getUser().getId().equals(user.getId())){
            recurringTaskById.setOrderNumber(this.recurringTaskRepository.countAllByUserAndStatus(user,Status.ARCHIVED)+1);
            recurringTaskById.setStatus(Status.ARCHIVED);
            fixOrderOfElements(Status.ACTIVE, recurringTaskById.getOrderNumber());
            this.recurringTaskRepository.save(recurringTaskById);
            return this.modelMapper.map(recurringTaskById,RecurringTaskModel.class);
        }
        return null;
    }

    private void fixOrderOfElements(Status status, Integer orderNumber) {
        List<RecurringTask> allByOrderNumberGreaterThan = this.recurringTaskRepository.findAllByStatusAndOrderNumberGreaterThan(status,orderNumber);
        if (!allByOrderNumberGreaterThan.isEmpty()){
            for (RecurringTask element : allByOrderNumberGreaterThan) {
                element.setOrderNumber(element.getOrderNumber()-1);
            }
        }
    }
}
