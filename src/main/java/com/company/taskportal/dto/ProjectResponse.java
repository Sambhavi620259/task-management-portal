package com.company.taskportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {

    private Long id;

    private String projectCode;

    private String projectName;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String projectManager;

    private Long organizationId;

    private String organizationName;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}