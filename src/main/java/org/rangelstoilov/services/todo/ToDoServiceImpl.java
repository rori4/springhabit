package org.rangelstoilov.services.todo;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.entities.ToDo;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.todo.ToDoModel;
import org.rangelstoilov.models.view.user.UserRewardModel;
import org.rangelstoilov.repositories.ToDoRepository;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public ToDoModel add(ToDoModel toDoAddModel, String userEmail) {
        User user = this.userService.findFirstByEmail(userEmail);
        ToDo todo = this.modelMapper.map(toDoAddModel, ToDo.class);
        todo.setUser(user);
        todo.setOrderNumber(this.toDoRepository.countAllByUserAndStatus(user,Status.ACTIVE)+1);
        return this.modelMapper.map(this.toDoRepository.save(todo),ToDoModel.class);
    }

    @Override
    public ToDoModel archive(String id, String userEmail) {
        User user = this.userService.findFirstByEmail(userEmail);
        ToDo toDoById = this.toDoRepository.findToDoById(id);
        if(toDoById.getUser().getId().equals(user.getId())){
            toDoById.setOrderNumber(this.toDoRepository.countAllByUserAndStatus(user,Status.ARCHIVED)+1);
            toDoById.setStatus(Status.ARCHIVED);
            fixOrderOfElements(Status.ACTIVE, toDoById.getOrderNumber());
            this.toDoRepository.save(toDoById);
            return this.modelMapper.map(toDoById,ToDoModel.class);
        }
        return null;
    }

    @Override
    public UserRewardModel markDone(String id, String userEmail) {
        ToDo toDoById = this.toDoRepository.findToDoById(id);
        User user = this.userService.findFirstByEmail(userEmail);
        if(toDoById.getUser().getId().equals(user.getId())){
            Integer orderNumber = toDoById.getOrderNumber();
            toDoById.setOrderNumber(this.toDoRepository.countAllByUserAndStatus(user,Status.DONE)+1);
            toDoById.setStatus(Status.DONE);
            fixOrderOfElements(Status.ACTIVE, orderNumber);
            toDoById.setCompletedOn(new Date());
            this.toDoRepository.save(toDoById);
            UserRewardModel userRewardModel = this.userService.rewardUserForTaskDone(userEmail, 1);
            return userRewardModel;
        } else {
            return null;
        }
    }

    @Override
    public List<ToDoModel> getAllToDos(Status status, String userEmail) {
        User firstByEmail = this.userService.findFirstByEmail(userEmail);
        List<ToDo> userToDos = this.toDoRepository.findAllByStatusAndUser(status,firstByEmail);
        java.lang.reflect.Type targetListType = new TypeToken<List<ToDoModel>>() {}.getType();
        return this.modelMapper.map(userToDos, targetListType);
    }

    @Override
    public ToDoModel findById(String id) {
        return this.modelMapper.map(this.toDoRepository.findToDoById(id),ToDoModel.class);
    }

    private void fixOrderOfElements(Status status, Integer orderNumber) {
        List<ToDo> allByOrderNumberGreaterThan = this.toDoRepository.findAllByStatusAndOrderNumberLessThan(status,orderNumber);
        if (!allByOrderNumberGreaterThan.isEmpty()){
            for (ToDo element : allByOrderNumberGreaterThan) {
                element.setOrderNumber(element.getOrderNumber()-1);
            }
        }
    }

}
