package com.company.taskportal.repository;

import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskChecklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskChecklistRepository extends JpaRepository<TaskChecklist, Long> {

    Optional<TaskChecklist> findByIdAndDeletedFalse(Long id);

    List<TaskChecklist> findByDeletedFalse();

    List<TaskChecklist> findByTaskAndDeletedFalse(Task task);

    List<TaskChecklist> findByAssignedToAndDeletedFalse(Employee assignedTo);

    List<TaskChecklist> findByCompletedAndDeletedFalse(Boolean completed);

    List<TaskChecklist> findByTaskAndCompletedAndDeletedFalse(
            Task task,
            Boolean completed
    );

    List<TaskChecklist> findByAssignedToAndCompletedAndDeletedFalse(
            Employee assignedTo,
            Boolean completed
    );

    List<TaskChecklist> findByTaskAndActiveTrueAndDeletedFalse(Task task);

    List<TaskChecklist> findByAssignedToAndActiveTrueAndDeletedFalse(
            Employee assignedTo
    );

    long countByTaskAndDeletedFalse(Task task);

    long countByAssignedToAndDeletedFalse(Employee assignedTo);

    long countByTaskAndCompletedAndDeletedFalse(
            Task task,
            Boolean completed
    );

    long countByAssignedToAndCompletedAndDeletedFalse(
            Employee assignedTo,
            Boolean completed
    );

}