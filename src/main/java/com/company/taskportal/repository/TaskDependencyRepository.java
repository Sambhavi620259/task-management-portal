package com.company.taskportal.repository;

import com.company.taskportal.entity.DependencyType;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {

    Optional<TaskDependency> findByIdAndDeletedFalse(Long id);

    List<TaskDependency> findByDeletedFalse();

    List<TaskDependency> findByPredecessorTaskAndDeletedFalse(
            Task predecessorTask
    );

    List<TaskDependency> findBySuccessorTaskAndDeletedFalse(
            Task successorTask
    );

    List<TaskDependency> findByDependencyTypeAndDeletedFalse(
            DependencyType dependencyType
    );

    List<TaskDependency> findByPredecessorTaskAndDependencyTypeAndDeletedFalse(
            Task predecessorTask,
            DependencyType dependencyType
    );

    List<TaskDependency> findBySuccessorTaskAndDependencyTypeAndDeletedFalse(
            Task successorTask,
            DependencyType dependencyType
    );

    Optional<TaskDependency>
    findByPredecessorTaskAndSuccessorTaskAndDeletedFalse(
            Task predecessorTask,
            Task successorTask
    );

    List<TaskDependency> findByPredecessorTaskAndActiveTrueAndDeletedFalse(
            Task predecessorTask
    );

    List<TaskDependency> findBySuccessorTaskAndActiveTrueAndDeletedFalse(
            Task successorTask
    );

    long countByPredecessorTaskAndDeletedFalse(
            Task predecessorTask
    );

    long countBySuccessorTaskAndDeletedFalse(
            Task successorTask
    );

    long countByDependencyTypeAndDeletedFalse(
            DependencyType dependencyType
    );

    boolean existsByPredecessorTaskAndSuccessorTaskAndDeletedFalse(
            Task predecessorTask,
            Task successorTask
    );

}