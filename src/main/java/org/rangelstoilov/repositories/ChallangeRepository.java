package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.RecurringTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallangeRepository extends JpaRepository<RecurringTask, String> {
}
