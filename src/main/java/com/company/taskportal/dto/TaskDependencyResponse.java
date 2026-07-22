package com.company.taskportal.dto;

import com.company.taskportal.entity.DependencyType;
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
public class TaskDependencyResponse {

    private Long id;

    private Long predecessorTaskId;
    private String predecessorTaskCode;
    private String predecessorTaskName;

    private Long successorTaskId;
    private String successorTaskCode;
    private String successorTaskName;

    private DependencyType dependencyType;

    private Boolean active;
    private Boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}