package com.company.taskportal.service;

import com.company.taskportal.dto.TaskNotificationRequest;
import com.company.taskportal.dto.TaskNotificationResponse;
import com.company.taskportal.entity.NotificationStatus;
import com.company.taskportal.entity.NotificationType;

import java.util.List;

public interface TaskNotificationService {

    TaskNotificationResponse createNotification(
            TaskNotificationRequest request
    );

    TaskNotificationResponse updateNotification(
            Long notificationId,
            TaskNotificationRequest request
    );

    TaskNotificationResponse getNotificationById(
            Long notificationId
    );

    List<TaskNotificationResponse> getAllNotifications();

    List<TaskNotificationResponse> getNotificationsByEmployee(
            Long employeeId
    );

    List<TaskNotificationResponse> getNotificationsByTask(
            Long taskId
    );

    List<TaskNotificationResponse> getNotificationsByType(
            NotificationType notificationType
    );

    List<TaskNotificationResponse> getNotificationsByStatus(
            NotificationStatus status
    );

    List<TaskNotificationResponse> getNotificationsByEmployeeAndStatus(
            Long employeeId,
            NotificationStatus status
    );

    List<TaskNotificationResponse> getNotificationsByEmployeeAndType(
            Long employeeId,
            NotificationType notificationType
    );

    List<TaskNotificationResponse> getNotificationsByTaskAndType(
            Long taskId,
            NotificationType notificationType
    );

    long countByEmployee(
            Long employeeId
    );

    long countByEmployeeAndStatus(
            Long employeeId,
            NotificationStatus status
    );

    long countByTask(
            Long taskId
    );

    long countByType(
            NotificationType notificationType
    );

    void markAsRead(
            Long notificationId
    );

    void markAsUnread(
            Long notificationId
    );

    void activateNotification(
            Long notificationId
    );

    void deactivateNotification(
            Long notificationId
    );

    void deleteNotification(
            Long notificationId
    );

}