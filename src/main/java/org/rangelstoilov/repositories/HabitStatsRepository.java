package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.Habit;
import org.rangelstoilov.entities.HabitStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HabitStatsRepository extends JpaRepository<HabitStats, String> {
    List<HabitStats> findAllByHabit(Habit habit);

    Integer countAllByHabit(Habit habit);

    List<HabitStats> findAllByCreatedOn(Date date);

}
