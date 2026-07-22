package com.company.taskportal.repository;

import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskTimeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskTimeLogRepository extends JpaRepository<TaskTimeLog, Long> {

    Optional<TaskTimeLog> findByIdAndDeletedFalse(Long id);

    List<TaskTimeLog> findByDeletedFalse();

    List<TaskTimeLog> findByTaskAndDeletedFalse(Task task);

    List<TaskTimeLog> findByEmployeeAndDeletedFalse(Employee employee);

    List<TaskTimeLog> findByWorkDateAndDeletedFalse(LocalDate workDate);

    List<TaskTimeLog> findByTaskAndWorkDateAndDeletedFalse(
            Task task,
            LocalDate workDate
    );

    List<TaskTimeLog> findByEmployeeAndWorkDateAndDeletedFalse(
            Employee employee,
            LocalDate workDate
    );

    List<TaskTimeLog> findByWorkDateBetweenAndDeletedFalse(
            LocalDate startDate,
            LocalDate endDate
    );

    List<TaskTimeLog> findByTaskAndWorkDateBetweenAndDeletedFalse(
            Task task,
            LocalDate startDate,
            LocalDate endDate
    );

    List<TaskTimeLog> findByEmployeeAndWorkDateBetweenAndDeletedFalse(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate
    );

    List<TaskTimeLog> findByTaskAndActiveTrueAndDeletedFalse(Task task);

    List<TaskTimeLog> findByEmployeeAndActiveTrueAndDeletedFalse(Employee employee);

    long countByTaskAndDeletedFalse(Task task);

    long countByEmployeeAndDeletedFalse(Employee employee);

    long countByWorkDateAndDeletedFalse(LocalDate workDate);

}