package org.rangelstoilov.repositories;

import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.entities.Habit;
import org.rangelstoilov.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, String> {
    List<Habit> findAllByTaskStatusAndUser(TaskStatus taskStatus, User user);

    Integer countAllByUserAndTaskStatus(User user, TaskStatus taskStatus);

    Habit findHabitById(String id);

    List<Habit> findAllByTaskStatusAndOrderNumberGreaterThan(TaskStatus taskStatus, Integer orderNumber);
}
