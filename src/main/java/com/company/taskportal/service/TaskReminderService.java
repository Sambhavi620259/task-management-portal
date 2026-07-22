package com.company.taskportal.service;

import com.company.taskportal.dto.TaskReminderRequest;
import com.company.taskportal.dto.TaskReminderResponse;
import com.company.taskportal.entity.ReminderType;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskReminderService {

    TaskReminderResponse createReminder(
            TaskReminderRequest request
    );

    TaskReminderResponse updateReminder(
            Long reminderId,
            TaskReminderRequest request
    );

    TaskReminderResponse getReminderById(
            Long reminderId
    );

    List<TaskReminderResponse> getAllReminders();

    List<TaskReminderResponse> getRemindersByTask(
            Long taskId
    );

    List<TaskReminderResponse> getRemindersByEmployee(
            Long employeeId
    );

    List<TaskReminderResponse> getRemindersByReminderType(
            ReminderType reminderType
    );

    List<TaskReminderResponse> getRemindersByTaskAndReminderType(
            Long taskId,
            ReminderType reminderType
    );

    List<TaskReminderResponse> getRemindersByEmployeeAndReminderType(
            Long employeeId,
            ReminderType reminderType
    );

    List<TaskReminderResponse> getRemindersBySentStatus(
            Boolean sent
    );

    List<TaskReminderResponse> getRemindersByTaskAndSentStatus(
            Long taskId,
            Boolean sent
    );

    List<TaskReminderResponse> getRemindersByEmployeeAndSentStatus(
            Long employeeId,
            Boolean sent
    );

    List<TaskReminderResponse> getDueReminders();

    List<TaskReminderResponse> getRemindersBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    long countByTask(
            Long taskId
    );

    long countByEmployee(
            Long employeeId
    );

    long countByReminderType(
            ReminderType reminderType
    );

    long countBySentStatus(
            Boolean sent
    );

    void markAsSent(
            Long reminderId
    );

    void activateReminder(
            Long reminderId
    );

    void deactivateReminder(
            Long reminderId
    );

    void deleteReminder(
            Long reminderId
    );

}