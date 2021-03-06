package org.rangelstoilov.services.habit;

import org.rangelstoilov.custom.enums.TaskStatus;
import org.rangelstoilov.models.view.habit.HabitModel;
import org.rangelstoilov.models.view.user.UserRewardModel;

import java.util.List;

public interface HabitService {
    HabitModel findById(String id);
    HabitModel add(HabitModel habitModel, String userEmail);

    UserRewardModel markPlus(String id, String userEmail);

    UserRewardModel markMinus(String id, String userEmail);

    HabitModel archive(String id, String userEmail);

    HabitModel delete(String id, String userEmail);

    HabitModel activate(String id, String userEmail);

    List<HabitModel> getAllHabitsOrdered(TaskStatus taskStatus, String userEmail);
}
