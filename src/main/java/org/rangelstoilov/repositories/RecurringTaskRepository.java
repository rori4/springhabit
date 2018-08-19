package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.RecurringTask;
import org.rangelstoilov.custom.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringTaskRepository extends JpaRepository<RecurringTask, String> {
    List<RecurringTask> findAllByStatus(Status status);
}