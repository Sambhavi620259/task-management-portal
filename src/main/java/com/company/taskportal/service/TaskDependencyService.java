package com.company.taskportal.service;

import com.company.taskportal.dto.TaskDependencyRequest;
import com.company.taskportal.dto.TaskDependencyResponse;
import com.company.taskportal.entity.DependencyType;

import java.util.List;

public interface TaskDependencyService {

    TaskDependencyResponse createDependency(
            TaskDependencyRequest request
    );

    TaskDependencyResponse updateDependency(
            Long dependencyId,
            TaskDependencyRequest request
    );

    TaskDependencyResponse getDependencyById(
            Long dependencyId
    );

    List<TaskDependencyResponse> getAllDependencies();

    List<TaskDependencyResponse> getDependenciesByPredecessorTask(
            Long predecessorTaskId
    );

    List<TaskDependencyResponse> getDependenciesBySuccessorTask(
            Long successorTaskId
    );

    List<TaskDependencyResponse> getDependenciesByType(
            DependencyType dependencyType
    );

    List<TaskDependencyResponse> getDependenciesByPredecessorTaskAndType(
            Long predecessorTaskId,
            DependencyType dependencyType
    );

    List<TaskDependencyResponse> getDependenciesBySuccessorTaskAndType(
            Long successorTaskId,
            DependencyType dependencyType
    );

    long countByPredecessorTask(
            Long predecessorTaskId
    );

    long countBySuccessorTask(
            Long successorTaskId
    );

    long countByDependencyType(
            DependencyType dependencyType
    );

    void activateDependency(
            Long dependencyId
    );

    void deactivateDependency(
            Long dependencyId
    );

    void deleteDependency(
            Long dependencyId
    );

}