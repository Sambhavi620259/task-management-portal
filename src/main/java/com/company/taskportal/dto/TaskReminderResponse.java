package com.company.taskportal.dto;

import com.company.taskportal.entity.ReminderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskReminderResponse {

    private Long id;

    private Long taskId;
    private String taskCode;
    private String taskName;

    private Long employeeId;
    private String employeeCode;
    private String employeeName;

    private ReminderType reminderType;

    private LocalDateTime reminderDateTime;

    private String message;

    private Boolean sent;

    private LocalDateTime sentAt;

    private Boolean active;
    private Boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}