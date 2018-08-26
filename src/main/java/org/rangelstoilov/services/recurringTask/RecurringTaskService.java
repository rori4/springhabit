package org.rangelstoilov.services.recurringTask;

import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.models.view.recurringTask.RecurringTaskModel;
import org.rangelstoilov.models.view.user.UserRewardModel;

import java.util.List;

public interface RecurringTaskService {
    RecurringTaskModel add(RecurringTaskModel recurringTaskModel, String userEmail);

    List<RecurringTaskModel> getAllRecurringTasks(TaskStatus taskStatus, String userEmail);

    UserRewardModel markDone(String id, String userEmail);

    RecurringTaskModel findById(String id);

    RecurringTaskModel archive(String id, String userEmail);

    RecurringTaskModel delete(String id, String userEmail);

    RecurringTaskModel activate(String id, String userEmail);
}
