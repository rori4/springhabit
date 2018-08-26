package org.rangelstoilov.repositories;

import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.entities.RecurringTask;
import org.rangelstoilov.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringTaskRepository extends JpaRepository<RecurringTask, String> {
    List<RecurringTask> findAllByTaskStatus(TaskStatus taskStatus);

    Integer countAllByUserAndTaskStatus(User user, TaskStatus taskStatus);

    List<RecurringTask> findAllByTaskStatusAndUser(TaskStatus taskStatus, User user);

    RecurringTask findRecurringTaskById(String id);

    List<RecurringTask> findAllByTaskStatusAndOrderNumberGreaterThan(TaskStatus taskStatus, Integer orderNumber);
}
