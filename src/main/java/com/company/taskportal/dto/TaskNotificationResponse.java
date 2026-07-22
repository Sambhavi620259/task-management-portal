package com.company.taskportal.dto;

import com.company.taskportal.entity.NotificationStatus;
import com.company.taskportal.entity.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskNotificationResponse {

    private Long id;

    private NotificationType notificationType;

    private NotificationStatus status;

    private String title;

    private String message;

    private Long taskId;
    private String taskCode;
    private String taskName;

    private Long employeeId;
    private String employeeCode;
    private String employeeName;

    private Boolean active;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}