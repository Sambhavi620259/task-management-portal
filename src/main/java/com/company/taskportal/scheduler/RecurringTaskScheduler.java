package com.company.taskportal.scheduler;

import com.company.taskportal.service.RecurringTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecurringTaskScheduler {

    private final RecurringTaskService recurringTaskService;

    /**
     * Runs every day at 12:00 AM.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void executeRecurringTaskGeneration() {

        log.info("Starting Recurring Task Scheduler...");

        try {

            recurringTaskService.generateRecurringTasks();

            log.info("Recurring Task Scheduler completed successfully.");

        } catch (Exception ex) {

            log.error(
                    "Recurring Task Scheduler failed: {}",
                    ex.getMessage(),
                    ex
            );
        }
    }

}