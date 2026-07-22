package com.company.taskportal.repository;

import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.NotificationStatus;
import com.company.taskportal.entity.NotificationType;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskNotificationRepository extends JpaRepository<TaskNotification, Long> {

    Optional<TaskNotification> findByIdAndDeletedFalse(Long id);

    List<TaskNotification> findByDeletedFalse();

    List<TaskNotification> findByEmployeeAndDeletedFalse(Employee employee);

    List<TaskNotification> findByTaskAndDeletedFalse(Task task);

    List<TaskNotification> findByNotificationTypeAndDeletedFalse(
            NotificationType notificationType
    );

    List<TaskNotification> findByStatusAndDeletedFalse(
            NotificationStatus status
    );

    List<TaskNotification> findByEmployeeAndStatusAndDeletedFalse(
            Employee employee,
            NotificationStatus status
    );

    List<TaskNotification> findByEmployeeAndNotificationTypeAndDeletedFalse(
            Employee employee,
            NotificationType notificationType
    );

    List<TaskNotification> findByTaskAndNotificationTypeAndDeletedFalse(
            Task task,
            NotificationType notificationType
    );

    List<TaskNotification> findByEmployeeAndActiveTrueAndDeletedFalse(
            Employee employee
    );

    List<TaskNotification> findByTaskAndActiveTrueAndDeletedFalse(
            Task task
    );

    long countByEmployeeAndDeletedFalse(Employee employee);

    long countByEmployeeAndStatusAndDeletedFalse(
            Employee employee,
            NotificationStatus status
    );

    long countByNotificationTypeAndDeletedFalse(
            NotificationType notificationType
    );

    long countByTaskAndDeletedFalse(Task task);

}