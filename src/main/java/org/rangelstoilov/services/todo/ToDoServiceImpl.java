package org.rangelstoilov.services.todo;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.entities.ToDo;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.todo.ToDoModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.repositories.ToDoRepository;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository, ModelMapper modelMapper, UserService userService) {
        this.toDoRepository = toDoRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @Override
    public List<ToDoModel> getAllToDos(TaskStatus taskStatus, String userEmail) {
        User firstByEmail = this.userService.findFirstByEmail(userEmail);
        List<ToDo> userToDos = this.toDoRepository.findAllByTaskStatusAndUser(taskStatus, firstByEmail);
        userToDos.sort(Comparator.comparing(ToDo::getOrderNumber));
        java.lang.reflect.Type targetListType = new TypeToken<List<ToDoModel>>() {
        }.getType();
        return this.modelMapper.map(userToDos, targetListType);
    }

    @Override
    public ToDoModel findById(String id) {
        return this.modelMapper.map(this.toDoRepository.findToDoById(id), ToDoModel.class);
    }

    @Override
    public ToDoModel add(ToDoModel toDoAddModel, String userEmail) {
        User user = this.userService.findFirstByEmail(userEmail);
        ToDo todo = this.modelMapper.map(toDoAddModel, ToDo.class);
        todo.setUser(user);
        todo.setOrderNumber(this.toDoRepository.countAllByUserAndTaskStatus(user, TaskStatus.ACTIVE) + 1);
        return this.modelMapper.map(this.toDoRepository.save(todo), ToDoModel.class);
    }

    @Override
    public UserRewardModel done(String id, String userEmail) {
        ToDo toDoById = this.toDoRepository.findToDoById(id);
        if (changeToDoStatus(userEmail, toDoById, TaskStatus.DONE)) {
            UserRewardModel userRewardModel = this.userService.rewardUserForTaskDone(userEmail, 1);
            return userRewardModel;
        } else return null;
    }

    @Override
    public ToDoModel activate(String id, String userEmail) {
        ToDo toDoById = this.toDoRepository.findToDoById(id);
        if (changeToDoStatus(userEmail, toDoById, TaskStatus.ACTIVE)) {
            return this.modelMapper.map(toDoById, ToDoModel.class);
        } else return null;
    }

    @Override
    public ToDoModel archive(String id, String userEmail) {
        ToDo toDoById = this.toDoRepository.findToDoById(id);
        if (changeToDoStatus(userEmail, toDoById, TaskStatus.ARCHIVED)) {
            return this.modelMapper.map(toDoById, ToDoModel.class);
        } else return null;
    }

    @Override
    public ToDoModel delete(String id, String userEmail) {
        ToDo toDoById = this.toDoRepository.findToDoById(id);
        if (changeToDoStatus(userEmail, toDoById, TaskStatus.DELETED)) {
            return this.modelMapper.map(toDoById, ToDoModel.class);
        } else return null;
    }

    private boolean changeToDoStatus(String userEmail, ToDo toDoById, TaskStatus taskStatus) {
        User user = this.userService.findFirstByEmail(userEmail);
        if (toDoById.getUser().getId().equals(user.getId())) {
            Integer orderNumber = toDoById.getOrderNumber();
            TaskStatus toDoByIdTaskStatus = toDoById.getTaskStatus();
            toDoById.setOrderNumber(this.toDoRepository.countAllByUserAndTaskStatus(user, taskStatus) + 1);
            toDoById.setTaskStatus(taskStatus);
            fixOrderOfElements(toDoByIdTaskStatus, orderNumber);
            toDoById.setCompletedOn(new Date());
            this.toDoRepository.save(toDoById);
            return true;
        } else {
            return false;
        }
    }


    private void fixOrderOfElements(TaskStatus taskStatus, Integer orderNumber) {
        List<ToDo> allByOrderNumberGreaterThan = this.toDoRepository.findAllByTaskStatusAndOrderNumberGreaterThan(taskStatus, orderNumber);
        if (!allByOrderNumberGreaterThan.isEmpty()) {
            for (ToDo element : allByOrderNumberGreaterThan) {
                element.setOrderNumber(element.getOrderNumber() - 1);
            }
        }
        this.toDoRepository.saveAll(allByOrderNumberGreaterThan);
    }

}
