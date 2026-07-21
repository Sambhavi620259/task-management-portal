package com.company.taskportal.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequest {

    @NotBlank(message = "Project code is required.")
    @Size(max = 20, message = "Project code cannot exceed 20 characters.")
    private String projectCode;

    @NotBlank(message = "Project name is required.")
    @Size(max = 150, message = "Project name cannot exceed 150 characters.")
    private String projectName;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters.")
    private String description;

    @NotNull(message = "Start date is required.")
    @FutureOrPresent(message = "Start date cannot be in the past.")
    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 100, message = "Project manager name cannot exceed 100 characters.")
    private String projectManager;

    @NotNull(message = "Organization ID is required.")
    private Long organizationId;

    private Boolean active;
}