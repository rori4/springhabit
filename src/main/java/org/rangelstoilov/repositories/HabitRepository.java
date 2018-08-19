package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.Habit;
import org.rangelstoilov.custom.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, String> {
    List<Habit> findAllByStatus(Status status);
}
