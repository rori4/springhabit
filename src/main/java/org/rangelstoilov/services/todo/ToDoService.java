package org.rangelstoilov.services.todo;

import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.models.view.todo.ToDoModel;

import java.util.List;

public interface ToDoService {

    ToDoModel add(ToDoModel toDoAddModel, String userEmail);

    boolean markDone(String id, String userEmail);

    List<ToDoModel> getAllToDos(Status status, String userEmail);
}
