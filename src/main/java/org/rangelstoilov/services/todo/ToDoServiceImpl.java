package org.rangelstoilov.services.todo;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.entities.ToDo;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.todo.ToDoModel;
import org.rangelstoilov.repositories.ToDoRepository;
import org.rangelstoilov.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public ToDoServiceImpl(ToDoRepository toDoRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ToDoModel add(ToDoModel toDoAddModel, String userEmail) {
        User user = this.userRepository.findFirstByEmail(userEmail);
        ToDo todo = this.modelMapper.map(toDoAddModel, ToDo.class);
        todo.setUser(user);
        todo.setOrderNumber(this.toDoRepository.countAllByUserAndStatus(user,Status.ACTIVE)+1);
        return this.modelMapper.map(this.toDoRepository.save(todo),ToDoModel.class);
    }

    @Override
    public boolean markDone(String id, String userEmail) {
        ToDo toDoById = this.toDoRepository.findToDoById(id);
        User user = this.userRepository.findFirstByEmail(userEmail);
        if(toDoById.getUser().getId().equals(user.getId())){
            Integer orderNumber = toDoById.getOrderNumber();
            fixOrderOfElements(orderNumber);
            toDoById.setOrderNumber(this.toDoRepository.countAllByUserAndStatus(user,Status.DONE)+1);
            toDoById.setStatus(Status.DONE);
            toDoById.setCompletedOn(new Date());
            this.toDoRepository.save(toDoById);
            //TODO: handle user level up, experience and rewards and fights
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ToDoModel> getAllToDos(Status status, String userEmail) {
        User firstByEmail = userRepository.findFirstByEmail(userEmail);
        List<ToDo> userToDos = this.toDoRepository.findAllByStatusAndUser(status,firstByEmail);
        java.lang.reflect.Type targetListType = new TypeToken<List<ToDoModel>>() {}.getType();
        return this.modelMapper.map(userToDos, targetListType);
    }

    private void fixOrderOfElements(Integer orderNumber) {
        List<ToDo> allByOrderNumberGreaterThan = this.toDoRepository.findAllByOrderNumberGreaterThan(orderNumber);
        if (!allByOrderNumberGreaterThan.isEmpty()){
            for (ToDo element : allByOrderNumberGreaterThan) {
                element.setOrderNumber(element.getOrderNumber()-1);
            }
        }
    }

}
