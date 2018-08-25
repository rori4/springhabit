package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.RecurringTask;
import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringTaskRepository extends JpaRepository<RecurringTask, String> {
    List<RecurringTask> findAllByStatus(Status status);

    Integer countAllByUserAndStatus(User user, Status status);

    List<RecurringTask> findAllByStatusAndUser(Status status, User user);

    RecurringTask findRecurringTaskById(String id);

    List<RecurringTask> findAllByStatusAndOrderNumberGreaterThan(Status status,Integer orderNumber);
}
