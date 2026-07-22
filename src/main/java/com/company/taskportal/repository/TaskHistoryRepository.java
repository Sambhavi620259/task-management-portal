package com.company.taskportal.repository;

import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskActionType;
import com.company.taskportal.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

    Optional<TaskHistory> findByIdAndDeletedFalse(Long id);

    List<TaskHistory> findByDeletedFalse();

    List<TaskHistory> findByTaskAndDeletedFalse(Task task);

    List<TaskHistory> findByPerformedByAndDeletedFalse(Employee performedBy);

    List<TaskHistory> findByActionTypeAndDeletedFalse(
            TaskActionType actionType
    );

    List<TaskHistory> findByTaskAndActionTypeAndDeletedFalse(
            Task task,
            TaskActionType actionType
    );

    List<TaskHistory> findByTaskAndActiveTrueAndDeletedFalse(
            Task task
    );

    List<TaskHistory> findByPerformedByAndActiveTrueAndDeletedFalse(
            Employee performedBy
    );

    List<TaskHistory> findByCreatedAtBetweenAndDeletedFalse(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    List<TaskHistory> findByTaskAndCreatedAtBetweenAndDeletedFalse(
            Task task,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    long countByTaskAndDeletedFalse(Task task);

    long countByPerformedByAndDeletedFalse(Employee performedBy);

    long countByActionTypeAndDeletedFalse(
            TaskActionType actionType
    );

}