package com.company.taskportal.controller;

import com.company.taskportal.dto.TaskNotificationRequest;
import com.company.taskportal.dto.TaskNotificationResponse;
import com.company.taskportal.entity.NotificationStatus;
import com.company.taskportal.entity.NotificationType;
import com.company.taskportal.service.TaskNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-notifications")
@RequiredArgsConstructor
public class TaskNotificationController {

    private final TaskNotificationService taskNotificationService;

    @PostMapping
    public ResponseEntity<TaskNotificationResponse> createNotification(
            @Valid @RequestBody TaskNotificationRequest request
    ) {

        return new ResponseEntity<>(
                taskNotificationService.createNotification(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<TaskNotificationResponse> updateNotification(
            @PathVariable Long notificationId,
            @Valid @RequestBody TaskNotificationRequest request
    ) {

        return ResponseEntity.ok(
                taskNotificationService.updateNotification(
                        notificationId,
                        request
                )
        );
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<TaskNotificationResponse> getNotificationById(
            @PathVariable Long notificationId
    ) {

        return ResponseEntity.ok(
                taskNotificationService.getNotificationById(notificationId)
        );
    }

    @GetMapping
    public ResponseEntity<List<TaskNotificationResponse>> getAllNotifications() {

        return ResponseEntity.ok(
                taskNotificationService.getAllNotifications()
        );
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<TaskNotificationResponse>> getNotificationsByEmployee(
            @PathVariable Long employeeId
    ) {

        return ResponseEntity.ok(
                taskNotificationService.getNotificationsByEmployee(employeeId)
        );
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskNotificationResponse>> getNotificationsByTask(
            @PathVariable Long taskId
    ) {

        return ResponseEntity.ok(
                taskNotificationService.getNotificationsByTask(taskId)
        );
    }

    @GetMapping("/type/{notificationType}")
    public ResponseEntity<List<TaskNotificationResponse>> getNotificationsByType(
            @PathVariable NotificationType notificationType
    ) {

        return ResponseEntity.ok(
                taskNotificationService.getNotificationsByType(notificationType)
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskNotificationResponse>> getNotificationsByStatus(
            @PathVariable NotificationStatus status
    ) {

        return ResponseEntity.ok(
                taskNotificationService.getNotificationsByStatus(status)
        );
    }

    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<List<TaskNotificationResponse>> getNotificationsByEmployeeAndStatus(
            @PathVariable Long employeeId,
            @PathVariable NotificationStatus status
    ) {

        return ResponseEntity.ok(
                taskNotificationService.getNotificationsByEmployeeAndStatus(
                        employeeId,
                        status
                )
        );
    }

    @GetMapping("/employee/{employeeId}/type/{notificationType}")
    public ResponseEntity<List<TaskNotificationResponse>> getNotificationsByEmployeeAndType(
            @PathVariable Long employeeId,
            @PathVariable NotificationType notificationType
    ) {

        return ResponseEntity.ok(
                taskNotificationService.getNotificationsByEmployeeAndType(
                        employeeId,
                        notificationType
                )
        );
    }

    @GetMapping("/task/{taskId}/type/{notificationType}")
    public ResponseEntity<List<TaskNotificationResponse>> getNotificationsByTaskAndType(
            @PathVariable Long taskId,
            @PathVariable NotificationType notificationType
    ) {

        return ResponseEntity.ok(
                taskNotificationService.getNotificationsByTaskAndType(
                        taskId,
                        notificationType
                )
        );
    }

    @GetMapping("/employee/{employeeId}/count")
    public ResponseEntity<Long> countByEmployee(
            @PathVariable Long employeeId
    ) {

        return ResponseEntity.ok(
                taskNotificationService.countByEmployee(employeeId)
        );
    }

    @GetMapping("/employee/{employeeId}/status/{status}/count")
    public ResponseEntity<Long> countByEmployeeAndStatus(
            @PathVariable Long employeeId,
            @PathVariable NotificationStatus status
    ) {

        return ResponseEntity.ok(
                taskNotificationService.countByEmployeeAndStatus(
                        employeeId,
                        status
                )
        );
    }

    @GetMapping("/task/{taskId}/count")
    public ResponseEntity<Long> countByTask(
            @PathVariable Long taskId
    ) {

        return ResponseEntity.ok(
                taskNotificationService.countByTask(taskId)
        );
    }

    @GetMapping("/type/{notificationType}/count")
    public ResponseEntity<Long> countByType(
            @PathVariable NotificationType notificationType
    ) {

        return ResponseEntity.ok(
                taskNotificationService.countByType(notificationType)
        );
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<String> markAsRead(
            @PathVariable Long notificationId
    ) {

        taskNotificationService.markAsRead(notificationId);

        return ResponseEntity.ok(
                "Notification marked as read successfully."
        );
    }

    @PatchMapping("/{notificationId}/unread")
    public ResponseEntity<String> markAsUnread(
            @PathVariable Long notificationId
    ) {

        taskNotificationService.markAsUnread(notificationId);

        return ResponseEntity.ok(
                "Notification marked as unread successfully."
        );
    }

    @PatchMapping("/{notificationId}/activate")
    public ResponseEntity<String> activateNotification(
            @PathVariable Long notificationId
    ) {

        taskNotificationService.activateNotification(notificationId);

        return ResponseEntity.ok(
                "Notification activated successfully."
        );
    }

    @PatchMapping("/{notificationId}/deactivate")
    public ResponseEntity<String> deactivateNotification(
            @PathVariable Long notificationId
    ) {

        taskNotificationService.deactivateNotification(notificationId);

        return ResponseEntity.ok(
                "Notification deactivated successfully."
        );
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long notificationId
    ) {

        taskNotificationService.deleteNotification(notificationId);

        return ResponseEntity.ok(
                "Notification deleted successfully."
        );
    }

}