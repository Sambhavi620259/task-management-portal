package com.company.taskportal.dto;

import com.company.taskportal.entity.NotificationStatus;
import com.company.taskportal.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskNotificationRequest {

    @NotNull(message = "Notification type is required")
    private NotificationType notificationType;

    @NotBlank(message = "Title is required")
    @Size(max = 500, message = "Title must not exceed 500 characters")
    private String title;

    @NotBlank(message = "Message is required")
    @Size(max = 3000, message = "Message must not exceed 3000 characters")
    private String message;

    private Long taskId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    private NotificationStatus status;

}