package org.rangelstoilov.services.todo;

import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.models.view.todo.ToDoModel;
import org.rangelstoilov.models.view.user.UserRewardModel;

import java.util.List;

public interface ToDoService {

    ToDoModel add(ToDoModel toDoAddModel, String userEmail);

    ToDoModel archive(String id, String userEmail);

    UserRewardModel markDone(String id, String userEmail);

    List<ToDoModel> getAllToDos(Status status, String userEmail);

    ToDoModel findById(String id);
}
