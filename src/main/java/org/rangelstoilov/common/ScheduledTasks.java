package org.rangelstoilov.common;

import org.rangelstoilov.custom.enums.Period;
import org.rangelstoilov.services.recurringTask.RecurringTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final RecurringTaskService recurringTaskService;

    @Autowired
    public ScheduledTasks(RecurringTaskService recurringTaskService) {
        this.recurringTaskService = recurringTaskService;
    }


//    @Scheduled(fixedRate = 1000)
//    public void scheduleFixedRateTask() {
//        System.out.println(
//                "Timestamp - " + System.currentTimeMillis() / 1000);
//    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkDailyRecurringTasks() {
        this.recurringTaskService.resetAllTasksByPeriod(Period.DAILY);
        System.out.println("ALL DAILY TASKS RESET: - " + System.currentTimeMillis() / 1000);
        this.recurringTaskService.doDamageForRecurringTasksNotDoneByPeriod(Period.DAILY);
        System.out.println("ALL MONTHLY TASKS DAMAGE DONE FOR NOT COMPLETE: - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron = "0 0 0 * * 2")
    public void checkWeeklyRecurringTasks() {
        this.recurringTaskService.resetAllTasksByPeriod(Period.WEEKLY);
        System.out.println("ALL WEEKLY TASKS RESET: - " + System.currentTimeMillis() / 1000);
        this.recurringTaskService.doDamageForRecurringTasksNotDoneByPeriod(Period.WEEKLY);
        System.out.println("ALL WEEKLY TASKS DAMAGE DONE FOR NOT COMPLETE: - " + System.currentTimeMillis() / 1000);
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void checkMonthlyRecurringTasks() {
        this.recurringTaskService.resetAllTasksByPeriod(Period.MONTHLY);
        System.out.println("ALL MONTHLY TASKS RESET: - " + System.currentTimeMillis() / 1000);
        this.recurringTaskService.doDamageForRecurringTasksNotDoneByPeriod(Period.MONTHLY);
        System.out.println("ALL MONTHLY TASKS DAMAGE DONE FOR NOT COMPLETE: - " + System.currentTimeMillis() / 1000);
    }
}
