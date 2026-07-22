package com.company.taskportal.scheduler;

import com.company.taskportal.service.OverdueTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OverdueTaskScheduler {

    private final OverdueTaskService overdueTaskService;

    @Scheduled(cron = "0 0 * * * *")
    public void processOverdueTasks() {

        log.info("Checking overdue tasks...");

        overdueTaskService.processOverdueTasks();
    }
}