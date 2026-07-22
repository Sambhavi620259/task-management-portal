package com.company.taskportal.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryResponse {

    /*----------------------------------------------------
     * Overall Statistics
     *---------------------------------------------------*/

    private Long totalTasks;

    private Long pendingTasks;

    private Long inProgressTasks;

    private Long completedTasks;

    private Long overdueTasks;

    /*----------------------------------------------------
     * Priority Statistics
     *---------------------------------------------------*/

    private Long lowPriorityTasks;

    private Long mediumPriorityTasks;

    private Long highPriorityTasks;

    private Long criticalPriorityTasks;

    /*----------------------------------------------------
     * Employee Statistics
     *---------------------------------------------------*/

    private Long totalEmployees;

    private Long activeEmployees;

    /*----------------------------------------------------
     * Organization Statistics
     *---------------------------------------------------*/

    private Long totalDepartments;

    private Long totalProjects;

    /*----------------------------------------------------
     * Performance
     *---------------------------------------------------*/

    private Double completionPercentage;

    private Double slaCompliancePercentage;

}