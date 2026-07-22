package com.company.taskportal.service;

import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.repository.TaskRepository;
import com.company.taskportal.service.EmailService;
import com.company.taskportal.service.OverdueTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OverdueTaskServiceImpl implements OverdueTaskService {

    private final TaskRepository taskRepository;
    private final EmailService emailService;

    @Override
    public void processOverdueTasks() {

        List<Task> tasks = taskRepository
                .findByDueDateBeforeAndStatusNotAndDeletedFalse(
                        LocalDate.now(),
                        TaskStatus.COMPLETED
                );

        for (Task task : tasks) {

            if (Boolean.TRUE.equals(task.getEmailNotification())) {
                emailService.sendOverdueTaskEmail(task);
            }
        }
    }
}