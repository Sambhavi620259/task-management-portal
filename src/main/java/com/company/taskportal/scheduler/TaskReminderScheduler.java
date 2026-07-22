package com.company.taskportal.scheduler;

import com.company.taskportal.service.ReminderProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskReminderScheduler {

    private final ReminderProcessorService reminderProcessorService;

    /**
     * Runs every minute.
     * Cron format:
     * second minute hour day month weekday
     */
    @Scheduled(cron = "0 * * * * *")
    public void executeReminderProcessor() {

        log.info("Starting Task Reminder Scheduler...");

        reminderProcessorService.processDueReminders();

        log.info("Task Reminder Scheduler completed.");
    }

}