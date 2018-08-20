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
    public boolean addToDo(ToDoModel toDoAddModel, String email) {
        User user = this.userRepository.findFirstByEmail(email);
        ToDo todo = this.modelMapper.map(toDoAddModel, ToDo.class);
        todo.setUser(user);
        this.toDoRepository.save(todo);
        return true;
    }

    @Override
    public List<ToDoModel> getAllToDos(Status status,String userEmail) {
        User firstByEmail = userRepository.findFirstByEmail(userEmail);
        List<ToDo> userToDos = this.toDoRepository.findAllByStatusAndUser(status,firstByEmail);
        java.lang.reflect.Type targetListType = new TypeToken<List<ToDoModel>>() {}.getType();
        return this.modelMapper.map(userToDos, targetListType);
    }

}
