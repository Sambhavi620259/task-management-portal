package com.company.taskportal.service;

import com.company.taskportal.dto.TaskDependencyRequest;
import com.company.taskportal.dto.TaskDependencyResponse;
import com.company.taskportal.entity.DependencyType;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskDependency;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.TaskDependencyRepository;
import com.company.taskportal.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
@RequiredArgsConstructor
@Transactional
public class TaskDependencyServiceImpl implements TaskDependencyService {
    private final TaskDependencyRepository taskDependencyRepository;

    private final TaskRepository taskRepository;
    private TaskDependency getDependency(Long dependencyId) {

        return taskDependencyRepository
                .findByIdAndDeletedFalse(dependencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task dependency not found with ID: " + dependencyId
                        ));
    }
    private Task getTask(Long taskId) {

        return taskRepository
                .findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with ID: " + taskId
                        ));
    }
    private boolean hasCircularDependency(
            Task predecessorTask,
            Task successorTask
    ) {

        return hasPath(
                successorTask,
                predecessorTask,
                new HashSet<>()
        );
    }    private boolean hasPath(
            Task currentTask,
            Task targetTask,
            Set<Long> visited
    ) {

        if (currentTask.getId().equals(targetTask.getId())) {
            return true;
        }

        if (!visited.add(currentTask.getId())) {
            return false;
        }

        List<TaskDependency> dependencies =
                taskDependencyRepository
                        .findByPredecessorTaskAndDeletedFalse(currentTask);

        for (TaskDependency dependency : dependencies) {

            if (hasPath(
                    dependency.getSuccessorTask(),
                    targetTask,
                    visited
            )) {
                return true;
            }
        }

        return false;
    }
    private TaskDependencyResponse mapToResponse(
            TaskDependency dependency
    ) {

        return TaskDependencyResponse.builder()
                .id(dependency.getId())

                .predecessorTaskId(
                        dependency.getPredecessorTask().getId()
                )
                .predecessorTaskCode(
                        dependency.getPredecessorTask().getTaskCode()
                )
                .predecessorTaskName(
                        dependency.getPredecessorTask().getTaskName()
                )

                .successorTaskId(
                        dependency.getSuccessorTask().getId()
                )
                .successorTaskCode(
                        dependency.getSuccessorTask().getTaskCode()
                )
                .successorTaskName(
                        dependency.getSuccessorTask().getTaskName()
                )

                .dependencyType(
                        dependency.getDependencyType()
                )

                .active(dependency.getActive())
                .deleted(dependency.getDeleted())
                .createdAt(dependency.getCreatedAt())
                .updatedAt(dependency.getUpdatedAt())
                .build();
    }
    @Override
    public TaskDependencyResponse createDependency(
            TaskDependencyRequest request
    ) {

        if (request.getPredecessorTaskId().equals(request.getSuccessorTaskId())) {
            throw new IllegalArgumentException(
                    "A task cannot depend on itself."
            );
        }

        Task predecessorTask = getTask(request.getPredecessorTaskId());
        Task successorTask = getTask(request.getSuccessorTaskId());

        if (taskDependencyRepository
                .existsByPredecessorTaskAndSuccessorTaskAndDeletedFalse(
                        predecessorTask,
                        successorTask
                )) {
            throw new IllegalArgumentException(
                    "Dependency already exists."
            );
        }

        if (hasCircularDependency(predecessorTask, successorTask)) {
            throw new IllegalArgumentException(
                    "Circular dependency detected."
            );
        }

        TaskDependency dependency = TaskDependency.builder()
                .predecessorTask(predecessorTask)
                .successorTask(successorTask)
                .dependencyType(request.getDependencyType())
                .build();

        dependency = taskDependencyRepository.save(dependency);

        return mapToResponse(dependency);
    }

    @Override
    public TaskDependencyResponse updateDependency(
            Long dependencyId,
            TaskDependencyRequest request
    ) {

        TaskDependency dependency = getDependency(dependencyId);

        if (request.getPredecessorTaskId().equals(request.getSuccessorTaskId())) {
            throw new IllegalArgumentException(
                    "A task cannot depend on itself."
            );
        }

        Task predecessorTask = getTask(request.getPredecessorTaskId());
        Task successorTask = getTask(request.getSuccessorTaskId());

        taskDependencyRepository
                .findByPredecessorTaskAndSuccessorTaskAndDeletedFalse(
                        predecessorTask,
                        successorTask
                )
                .ifPresent(existing -> {
                    if (!existing.getId().equals(dependencyId)) {
                        throw new IllegalArgumentException(
                                "Dependency already exists."
                        );
                    }
                });

        if (hasCircularDependency(predecessorTask, successorTask)) {
            throw new IllegalArgumentException(
                    "Circular dependency detected."
            );
        }

        dependency.setPredecessorTask(predecessorTask);
        dependency.setSuccessorTask(successorTask);
        dependency.setDependencyType(request.getDependencyType());

        dependency = taskDependencyRepository.save(dependency);

        return mapToResponse(dependency);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDependencyResponse getDependencyById(
            Long dependencyId
    ) {

        return mapToResponse(getDependency(dependencyId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDependencyResponse> getAllDependencies() {

        return taskDependencyRepository
                .findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }    @Override
    @Transactional(readOnly = true)
    public List<TaskDependencyResponse> getDependenciesByPredecessorTask(
            Long predecessorTaskId
    ) {

        Task predecessorTask = getTask(predecessorTaskId);

        return taskDependencyRepository
                .findByPredecessorTaskAndDeletedFalse(predecessorTask)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDependencyResponse> getDependenciesBySuccessorTask(
            Long successorTaskId
    ) {

        Task successorTask = getTask(successorTaskId);

        return taskDependencyRepository
                .findBySuccessorTaskAndDeletedFalse(successorTask)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDependencyResponse> getDependenciesByType(
            DependencyType dependencyType
    ) {

        return taskDependencyRepository
                .findByDependencyTypeAndDeletedFalse(dependencyType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDependencyResponse> getDependenciesByPredecessorTaskAndType(
            Long predecessorTaskId,
            DependencyType dependencyType
    ) {

        Task predecessorTask = getTask(predecessorTaskId);

        return taskDependencyRepository
                .findByPredecessorTaskAndDependencyTypeAndDeletedFalse(
                        predecessorTask,
                        dependencyType
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDependencyResponse> getDependenciesBySuccessorTaskAndType(
            Long successorTaskId,
            DependencyType dependencyType
    ) {

        Task successorTask = getTask(successorTaskId);

        return taskDependencyRepository
                .findBySuccessorTaskAndDependencyTypeAndDeletedFalse(
                        successorTask,
                        dependencyType
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }    @Override
    @Transactional(readOnly = true)
    public long countByPredecessorTask(
            Long predecessorTaskId
    ) {

        Task predecessorTask = getTask(predecessorTaskId);

        return taskDependencyRepository
                .countByPredecessorTaskAndDeletedFalse(
                        predecessorTask
                );
    }

    @Override
    @Transactional(readOnly = true)
    public long countBySuccessorTask(
            Long successorTaskId
    ) {

        Task successorTask = getTask(successorTaskId);

        return taskDependencyRepository
                .countBySuccessorTaskAndDeletedFalse(
                        successorTask
                );
    }

    @Override
    @Transactional(readOnly = true)
    public long countByDependencyType(
            DependencyType dependencyType
    ) {

        return taskDependencyRepository
                .countByDependencyTypeAndDeletedFalse(
                        dependencyType
                );
    }

    @Override
    public void activateDependency(
            Long dependencyId
    ) {

        TaskDependency dependency = getDependency(dependencyId);

        dependency.setActive(true);

        taskDependencyRepository.save(dependency);
    }

    @Override
    public void deactivateDependency(
            Long dependencyId
    ) {

        TaskDependency dependency = getDependency(dependencyId);

        dependency.setActive(false);

        taskDependencyRepository.save(dependency);
    }

    @Override
    public void deleteDependency(
            Long dependencyId
    ) {

        TaskDependency dependency = getDependency(dependencyId);

        dependency.setDeleted(true);
        dependency.setActive(false);

        taskDependencyRepository.save(dependency);
    }


}
