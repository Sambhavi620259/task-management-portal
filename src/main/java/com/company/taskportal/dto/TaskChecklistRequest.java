package com.company.taskportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskChecklistRequest {

    @NotBlank(message = "Checklist title is required")
    @Size(max = 500, message = "Checklist title must not exceed 500 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @NotNull(message = "Task ID is required")
    private Long taskId;

    private Long assignedToId;

    private Boolean completed;

}