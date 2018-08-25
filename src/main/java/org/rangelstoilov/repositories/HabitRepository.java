package org.rangelstoilov.repositories;

import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.entities.Habit;
import org.rangelstoilov.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, String> {
    List<Habit> findAllByStatusAndUser(Status status, User user);

    Integer countAllByUserAndStatus(User user, Status status);

    Habit findHabitById(String id);

    List<Habit> findAllByStatusAndOrderNumberGreaterThan(Status status, Integer orderNumber);
}
