package com.company.taskportal.service;

import com.company.taskportal.entity.Frequency;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecurringTaskServiceImpl
        implements RecurringTaskService {

    private final TaskRepository taskRepository;

    @Override
    public void generateRecurringTasks() {

        List<Task> recurringTasks =
                taskRepository.findByAutoGenerateNextTrueAndDeletedFalse();

        if (recurringTasks.isEmpty()) {
            log.debug("No recurring tasks available.");
            return;
        }

        LocalDate today = LocalDate.now();

        for (Task task : recurringTasks) {

            if (task.getNextExecutionDate() == null) {
                continue;
            }

            if (task.getNextExecutionDate().isAfter(today)) {
                continue;
            }

            generateNextTask(task);
        }
    }

    private void generateNextTask(Task sourceTask) {

        Task newTask = Task.builder()
                .taskCode(generateTaskCode(sourceTask))
                .taskName(sourceTask.getTaskName())
                .description(sourceTask.getDescription())

                .organization(sourceTask.getOrganization())
                .department(sourceTask.getDepartment())
                .project(sourceTask.getProject())
                .taskCategory(sourceTask.getTaskCategory())
                .frequency(sourceTask.getFrequency())

                .priority(sourceTask.getPriority())
                .status(TaskStatus.PENDING)
                .taskType(sourceTask.getTaskType())

                .assignedTo(sourceTask.getAssignedTo())
                .createdBy(sourceTask.getCreatedBy())
                .reviewedBy(sourceTask.getReviewedBy())
                .approvedBy(sourceTask.getApprovedBy())

                .workflowRequired(sourceTask.getWorkflowRequired())
                .approvalRequired(sourceTask.getApprovalRequired())
                .workflowStep(sourceTask.getWorkflowStep())

                .estimatedHours(sourceTask.getEstimatedHours())
                .slaHours(sourceTask.getSlaHours())
                .reminderBeforeHours(sourceTask.getReminderBeforeHours())
                .escalationAfterHours(sourceTask.getEscalationAfterHours())

                .emailNotification(sourceTask.getEmailNotification())
                .smsNotification(sourceTask.getSmsNotification())
                .pushNotification(sourceTask.getPushNotification())

                .completionPercentage(0)

                .startDate(sourceTask.getNextExecutionDate())
                .dueDate(sourceTask.getDueDate())

                .autoGenerateNext(sourceTask.getAutoGenerateNext())

                .build();

        taskRepository.save(newTask);

        updateNextExecution(sourceTask);

        log.info(
                "Recurring task generated from task {}",
                sourceTask.getTaskCode()
        );
    }
    private void updateNextExecution(Task task) {

        LocalDate currentExecutionDate = task.getNextExecutionDate();

        task.setLastExecutionDate(currentExecutionDate);
        task.setNextExecutionDate(
                calculateNextExecutionDate(
                        currentExecutionDate,
                        task.getFrequency()
                )
        );

        taskRepository.save(task);
    }

    private LocalDate calculateNextExecutionDate(
            LocalDate currentDate,
            Frequency frequency
    ) {

        if (frequency == null) {
            return currentDate;
        }

        String frequencyName = frequency.getFrequencyName();

        if (frequencyName == null) {
            return currentDate;
        }

        switch (frequencyName.trim().toUpperCase()) {

            case "DAILY":
                return currentDate.plusDays(1);

            case "WEEKLY":
                return currentDate.plusWeeks(1);

            case "FORTNIGHTLY":
                return currentDate.plusWeeks(2);

            case "MONTHLY":
                return currentDate.plusMonths(1);

            case "QUARTERLY":
                return currentDate.plusMonths(3);

            case "HALF_YEARLY":
                return currentDate.plusMonths(6);

            case "YEARLY":
                return currentDate.plusYears(1);

            default:
                log.warn(
                        "Unsupported frequency '{}' for task scheduling.",
                        frequencyName
                );
                return currentDate;
        }
    }

    private String generateTaskCode(Task sourceTask) {

        return sourceTask.getTaskCode()
                + "-"
                + System.currentTimeMillis();
    }

}