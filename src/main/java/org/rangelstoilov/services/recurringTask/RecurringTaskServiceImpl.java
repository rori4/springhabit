package org.rangelstoilov.services.recurringTask;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.custom.enums.Period;
import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.entities.RecurringTask;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.recurringTask.RecurringTaskModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.repositories.RecurringTaskRepository;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
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
    public RecurringTaskModel findById(String id) {
        return this.modelMapper.map(this.recurringTaskRepository.findRecurringTaskById(id),RecurringTaskModel.class);
    }

    @Override
    public RecurringTaskModel add(RecurringTaskModel recurringTaskModel, String userEmail) {
        User user = this.userService.findFirstByEmail(userEmail);
        RecurringTask recurringTask = this.modelMapper.map(recurringTaskModel, RecurringTask.class);
        recurringTask.setUser(user);
        recurringTask.setOrderNumber(this.recurringTaskRepository.countAllByUserAndTaskStatus(user, TaskStatus.ACTIVE)+ 1);
        return this.modelMapper.map(this.recurringTaskRepository.save(recurringTask), RecurringTaskModel.class);
    }

    @Override
    public List<RecurringTaskModel> getAllRecurringTasks(TaskStatus taskStatus, String userEmail) {
        User firstByEmail = this.userService.findFirstByEmail(userEmail);
        List<RecurringTask> userToDos = this.recurringTaskRepository.findAllByTaskStatusAndUser(taskStatus,firstByEmail);
        userToDos.sort(Comparator.comparing(RecurringTask::getOrderNumber));
        java.lang.reflect.Type targetListType = new TypeToken<List<RecurringTaskModel>>() {}.getType();
        return this.modelMapper.map(userToDos, targetListType);
    }

    @Override
    public UserRewardModel markDone(String id, String userEmail) {
        RecurringTask recurringTaskById = this.recurringTaskRepository.findRecurringTaskById(id);
        if(changeRecurringTaskStatus(userEmail, recurringTaskById, TaskStatus.DONE)){
            UserRewardModel userRewardModel = this.userService.rewardUserForTaskDone(userEmail, 1);
            return userRewardModel;
        } else return null;
    }

    @Override
    public RecurringTaskModel archive(String id, String userEmail) {
        RecurringTask recurringTaskById = this.recurringTaskRepository.findRecurringTaskById(id);
        if(changeRecurringTaskStatus(userEmail,recurringTaskById, TaskStatus.ARCHIVED)){
            return this.modelMapper.map(recurringTaskById,RecurringTaskModel.class);
        } else return null;
    }

    @Override
    public RecurringTaskModel delete(String id, String userEmail) {
        RecurringTask recurringTaskById = this.recurringTaskRepository.findRecurringTaskById(id);
        if(changeRecurringTaskStatus(userEmail,recurringTaskById, TaskStatus.DELETED)){
            return this.modelMapper.map(recurringTaskById,RecurringTaskModel.class);
        } else return null;
    }

    @Override
    public RecurringTaskModel activate(String id, String userEmail) {
        RecurringTask recurringTaskById = this.recurringTaskRepository.findRecurringTaskById(id);
        if(changeRecurringTaskStatus(userEmail,recurringTaskById, TaskStatus.ACTIVE)){
            return this.modelMapper.map(recurringTaskById,RecurringTaskModel.class);
        } else return null;
    }
    @Override
    public void resetAllTasksByPeriod(Period period){
        List<RecurringTask> allByTaskStatusAndResetPeriod = this.recurringTaskRepository.findAllByTaskStatusAndResetPeriod(TaskStatus.DONE, period);
        if(!allByTaskStatusAndResetPeriod.isEmpty()){
            for (RecurringTask task: allByTaskStatusAndResetPeriod) {
                User user = task.getUser();
                changeRecurringTaskStatus(task,TaskStatus.ACTIVE,user);
            }
        }
    }

    @Override
    public void doDamageForRecurringTasksNotDoneByPeriod(Period period){
        List<RecurringTask> allByTaskStatusAndResetPeriod = this.recurringTaskRepository.findAllByTaskStatusAndResetPeriod(TaskStatus.ACTIVE, period);
        if(!allByTaskStatusAndResetPeriod.isEmpty()){
            for (RecurringTask task: allByTaskStatusAndResetPeriod) {
                User user = task.getUser();
                this.userService.damageUserForTaskNotDone(user.getEmail(),1);
            }
        }
    }

    private boolean changeRecurringTaskStatus(String userEmail, RecurringTask recurringTaskById, TaskStatus taskStatus) {
        User user = this.userService.findFirstByEmail(userEmail);
        if(recurringTaskById.getUser().getId().equals(user.getId())){
            changeRecurringTaskStatus(recurringTaskById, taskStatus, user);
            return true;
        } else return false;
    }

    private void changeRecurringTaskStatus(RecurringTask recurringTaskById, TaskStatus taskStatus, User user) {
        Integer orderNumber = recurringTaskById.getOrderNumber();
        TaskStatus recurringTaskByIdTaskStatus = recurringTaskById.getTaskStatus();
        recurringTaskById.setOrderNumber(this.recurringTaskRepository.countAllByUserAndTaskStatus(user, taskStatus)+1);
        recurringTaskById.setTaskStatus(taskStatus);
        fixOrderOfElements(recurringTaskByIdTaskStatus, orderNumber);
        recurringTaskById.setCount(recurringTaskById.getCount()+1);
        this.recurringTaskRepository.save(recurringTaskById);
    }

    private void fixOrderOfElements(TaskStatus taskStatus, Integer orderNumber) {
        List<RecurringTask> allByOrderNumberGreaterThan = this.recurringTaskRepository.findAllByTaskStatusAndOrderNumberGreaterThan(taskStatus,orderNumber);
        if (!allByOrderNumberGreaterThan.isEmpty()){
            for (RecurringTask element : allByOrderNumberGreaterThan) {
                element.setOrderNumber(element.getOrderNumber()-1);
            }
        }
        this.recurringTaskRepository.saveAll(allByOrderNumberGreaterThan);
    }
}
