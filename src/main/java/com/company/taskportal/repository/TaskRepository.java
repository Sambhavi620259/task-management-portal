package com.company.taskportal.repository;

import com.company.taskportal.entity.Department;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Frequency;
import com.company.taskportal.entity.Organization;
import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.Project;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskCategory;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.entity.TaskType;
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

    List<Task> findByDueDateAndDeletedFalse(LocalDate dueDate);

    List<Task> findByStartDateAndDeletedFalse(LocalDate startDate);

    List<Task> findByNextExecutionDateAndDeletedFalse(LocalDate nextExecutionDate);

    /**
     * Recurring Task Engine
     */
    List<Task> findByAutoGenerateNextTrueAndDeletedFalse();

    /**
     * Dashboard Statistics
     */
    long countByDeletedFalse();

    long countByStatusAndDeletedFalse(TaskStatus status);

    long countByPriorityAndDeletedFalse(Priority priority);

    long countByAssignedToAndDeletedFalse(Employee employee);

    long countByDueDateBeforeAndStatusNotAndDeletedFalse(
            LocalDate dueDate,
            TaskStatus status
    );
}