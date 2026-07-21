package com.company.taskportal.repository;

import com.company.taskportal.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndDeletedFalse(Long id);

    Optional<Task> findByTaskCodeAndDeletedFalse(String taskCode);

    boolean existsByTaskCode(String taskCode);

    boolean existsByTaskCodeAndDeletedFalse(String taskCode);

    List<Task> findByDeletedFalse();

    List<Task> findByActiveTrueAndDeletedFalse();

    List<Task> findByStatusAndDeletedFalse(TaskStatus status);

    List<Task> findByPriorityAndDeletedFalse(Priority priority);

    List<Task> findByTaskTypeAndDeletedFalse(TaskType taskType);

    List<Task> findByOrganizationAndDeletedFalse(Organization organization);

    List<Task> findByDepartmentAndDeletedFalse(Department department);

    List<Task> findByProjectAndDeletedFalse(Project project);

    List<Task> findByTaskCategoryAndDeletedFalse(TaskCategory taskCategory);

    List<Task> findByFrequencyAndDeletedFalse(Frequency frequency);

    List<Task> findByAssignedToAndDeletedFalse(Employee assignedTo);

    List<Task> findByCreatedByAndDeletedFalse(Employee createdBy);

    List<Task> findByReviewedByAndDeletedFalse(Employee reviewedBy);

    List<Task> findByApprovedByAndDeletedFalse(Employee approvedBy);

    List<Task> findByDueDateBeforeAndStatusNotAndDeletedFalse(
            LocalDate dueDate,
            TaskStatus status
    );

    List<Task> findByDueDate(LocalDate dueDate);

    List<Task> findByStartDate(LocalDate startDate);

    List<Task> findByNextExecutionDate(LocalDate nextExecutionDate);

    long countByStatusAndDeletedFalse(TaskStatus status);

    long countByPriorityAndDeletedFalse(Priority priority);

    long countByAssignedToAndDeletedFalse(Employee employee);

}