package com.company.taskportal.repository;

import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.ReminderType;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskReminderRepository
        extends JpaRepository<TaskReminder, Long> {

    Optional<TaskReminder> findByIdAndDeletedFalse(
            Long id
    );

    List<TaskReminder> findByDeletedFalse();

    List<TaskReminder> findByTaskAndDeletedFalse(
            Task task
    );

    List<TaskReminder> findByEmployeeAndDeletedFalse(
            Employee employee
    );

    List<TaskReminder> findByReminderTypeAndDeletedFalse(
            ReminderType reminderType
    );

    List<TaskReminder> findBySentAndDeletedFalse(
            Boolean sent
    );

    List<TaskReminder> findByTaskAndReminderTypeAndDeletedFalse(
            Task task,
            ReminderType reminderType
    );

    List<TaskReminder> findByEmployeeAndReminderTypeAndDeletedFalse(
            Employee employee,
            ReminderType reminderType
    );

    List<TaskReminder> findByTaskAndSentAndDeletedFalse(
            Task task,
            Boolean sent
    );

    List<TaskReminder> findByEmployeeAndSentAndDeletedFalse(
            Employee employee,
            Boolean sent
    );

    List<TaskReminder>
    findByReminderDateTimeLessThanEqualAndSentFalseAndDeletedFalse(
            LocalDateTime reminderDateTime
    );

    List<TaskReminder>
    findByReminderDateTimeBetweenAndDeletedFalse(
            LocalDateTime start,
            LocalDateTime end
    );

    List<TaskReminder>
    findByTaskAndEmployeeAndDeletedFalse(
            Task task,
            Employee employee
    );

    long countByTaskAndDeletedFalse(
            Task task
    );

    long countByEmployeeAndDeletedFalse(
            Employee employee
    );

    long countByReminderTypeAndDeletedFalse(
            ReminderType reminderType
    );

    long countBySentAndDeletedFalse(
            Boolean sent
    );

    boolean existsByTaskAndEmployeeAndReminderDateTimeAndDeletedFalse(
            Task task,
            Employee employee,
            LocalDateTime reminderDateTime
    );

}