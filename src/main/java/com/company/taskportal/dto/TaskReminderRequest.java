package com.company.taskportal.dto;

import com.company.taskportal.entity.ReminderType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class TaskReminderRequest {

    @NotNull(message = "Task ID is required")
    private Long taskId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Reminder type is required")
    private ReminderType reminderType;

    @NotNull(message = "Reminder date and time are required")
    @Future(message = "Reminder date and time must be in the future")
    private LocalDateTime reminderDateTime;

    @Size(
            max = 1000,
            message = "Message cannot exceed 1000 characters"
    )
    private String message;

}