package org.rangelstoilov.services.todo;

import org.rangelstoilov.entities.ToDo;
import org.rangelstoilov.models.view.ToDoAddModel;

public interface ToDoService {
    ToDo addToDo(ToDoAddModel toDoAddModel, String username);
}
