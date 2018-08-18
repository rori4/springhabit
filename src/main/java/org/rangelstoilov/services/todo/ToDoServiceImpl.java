package org.rangelstoilov.services.todo;

import org.modelmapper.ModelMapper;
import org.rangelstoilov.entities.ToDo;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.view.ToDoAddModel;
import org.rangelstoilov.repositories.ToDoRepository;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public ToDo addToDo(ToDoAddModel toDoAddModel, String email) {
        User user = this.userService.findUserEntityByEmail(email);
        ToDo todo = this.modelMapper.map(toDoAddModel, ToDo.class);
        todo.setUser(user);
        return this.toDoRepository.save(todo);
    }
}
