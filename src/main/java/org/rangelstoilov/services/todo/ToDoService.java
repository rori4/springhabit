package org.rangelstoilov.services.todo;

import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.models.view.todo.ToDoModel;
import org.rangelstoilov.models.view.user.UserRewardModel;

import java.util.List;

public interface ToDoService {

    ToDoModel add(ToDoModel toDoAddModel, String userEmail);

    ToDoModel activate(String id, String userEmail);

    ToDoModel archive(String id, String userEmail);

    UserRewardModel done(String id, String userEmail);

    List<ToDoModel> getAllToDos(TaskStatus taskStatus, String userEmail);

    ToDoModel findById(String id);

    ToDoModel delete(String id, String userEmail);
}
