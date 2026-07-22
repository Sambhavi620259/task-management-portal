package com.company.taskportal.dto;

import com.company.taskportal.entity.DependencyType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDependencyRequest {

    @NotNull(message = "Predecessor task ID is required")
    private Long predecessorTaskId;

    @NotNull(message = "Successor task ID is required")
    private Long successorTaskId;

    @NotNull(message = "Dependency type is required")
    private DependencyType dependencyType;

}