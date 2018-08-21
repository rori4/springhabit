package org.rangelstoilov.services.habit;

import org.rangelstoilov.custom.enums.Status;
import org.rangelstoilov.models.view.habit.HabitModel;

import java.util.List;

public interface HabitService {
    HabitModel add(HabitModel habitModel, String userEmail);

    boolean markPlus(String id, String userEmail);

    boolean markMinus(String id, String userEmail);

    boolean archive(String id, String userEmail);

    List<HabitModel> getAllHabits(Status status, String userEmail);
}
