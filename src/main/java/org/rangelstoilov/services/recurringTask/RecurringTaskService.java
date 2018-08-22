package org.rangelstoilov.services.recurringTask;

import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.models.view.recurringTask.RecurringTaskModel;

import java.util.List;

public interface RecurringTaskService {
    RecurringTaskModel add(RecurringTaskModel recurringTaskModel, String userEmail);

    List<RecurringTaskModel> getAllRecurringTasks(Status status, String userEmail);

    boolean markDone(String id, String userEmail);
}
