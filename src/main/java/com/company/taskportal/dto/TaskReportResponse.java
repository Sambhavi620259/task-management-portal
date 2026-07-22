package com.company.taskportal.dto;

import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.entity.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskReportResponse {

    private Long taskId;

    private String taskCode;

    private String taskName;

    private String organization;

    private String department;

    private String project;

    private String category;

    private String assignedEmployee;

    private Priority priority;

    private TaskStatus status;

    private TaskType taskType;

    private LocalDate startDate;

    private LocalDate dueDate;

    private LocalDate completedDate;

    private Integer estimatedHours;

    private Integer actualHours;

    private Integer completionPercentage;

}