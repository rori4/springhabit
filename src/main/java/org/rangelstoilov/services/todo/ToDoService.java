package org.rangelstoilov.services.todo;

import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.models.view.todo.ToDoModel;

import java.util.List;

public interface ToDoService {
    void addToDo(ToDoModel toDoAddModel, String username);

    List<ToDoModel> getAllToDos(Status status, String email);
}
