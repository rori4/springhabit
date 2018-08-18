package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.RecurringTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<RecurringTask, String> {
}
