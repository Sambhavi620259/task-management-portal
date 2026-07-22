package com.company.taskportal.dto;

import com.company.taskportal.entity.TaskActionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskHistoryRequest {

    @NotNull(message = "Task ID is required.")
    private Long taskId;

    @NotNull(message = "Employee ID is required.")
    private Long performedById;

    @NotNull(message = "Action type is required.")
    private TaskActionType actionType;

    @NotBlank(message = "Description is required.")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters.")
    private String description;

    @Size(max = 1000, message = "Old value cannot exceed 1000 characters.")
    private String oldValue;

    @Size(max = 1000, message = "New value cannot exceed 1000 characters.")
    private String newValue;

}