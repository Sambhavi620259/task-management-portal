package com.company.taskportal.service;

import com.company.taskportal.dto.TaskNotificationRequest;
import com.company.taskportal.dto.TaskNotificationResponse;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.NotificationStatus;
import com.company.taskportal.entity.NotificationType;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskNotification;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.TaskNotificationRepository;
import com.company.taskportal.repository.TaskRepository;
import com.company.taskportal.service.TaskNotificationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskNotificationServiceImpl implements TaskNotificationService {

    private final TaskNotificationRepository taskNotificationRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    private TaskNotification getNotification(Long notificationId) {
        return taskNotificationRepository
                .findByIdAndDeletedFalse(notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification not found with ID: " + notificationId
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

    private Employee getEmployee(Long employeeId) {
        return employeeRepository
                .findByIdAndDeletedFalse(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + employeeId
                        ));
    }

    private TaskNotification mapToEntity(
            TaskNotificationRequest request
    ) {
        Task task = null;

        if (request.getTaskId() != null) {
            task = getTask(request.getTaskId());
        }

        Employee employee = getEmployee(request.getEmployeeId());

        return TaskNotification.builder()
                .notificationType(request.getNotificationType())
                .status(
                        Optional.ofNullable(request.getStatus())
                                .orElse(NotificationStatus.UNREAD)
                )
                .title(request.getTitle())
                .message(request.getMessage())
                .task(task)
                .employee(employee)
                .build();
    }

    private TaskNotificationResponse mapToResponse(
            TaskNotification notification
    ) {
        Long employeeId = null;
        String employeeCode = null;
        String employeeName = null;

        if (notification.getEmployee() != null) {
            Employee employee = notification.getEmployee();
            employeeId = employee.getId();
            employeeCode = employee.getEmployeeCode();
            employeeName = Stream.of(
                            employee.getFirstName(),
                            employee.getLastName()
                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "))
                    .trim();
        }

        return TaskNotificationResponse.builder()
                .id(notification.getId())

                .notificationType(notification.getNotificationType())
                .status(notification.getStatus())

                .title(notification.getTitle())
                .message(notification.getMessage())

                .taskId(
                        notification.getTask() != null
                                ? notification.getTask().getId()
                                : null
                )
                .taskCode(
                        notification.getTask() != null
                                ? notification.getTask().getTaskCode()
                                : null
                )
                .taskName(
                        notification.getTask() != null
                                ? notification.getTask().getTaskName()
                                : null
                )

                .employeeId(employeeId)
                .employeeCode(employeeCode)
                .employeeName(employeeName)

                .active(notification.getActive())
                .deleted(notification.getDeleted())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }

    @Override
    public TaskNotificationResponse createNotification(
            TaskNotificationRequest request
    ) {
        TaskNotification notification = mapToEntity(request);

        notification = taskNotificationRepository.save(notification);

        return mapToResponse(notification);
    }

    @Override
    public TaskNotificationResponse updateNotification(
            Long notificationId,
            TaskNotificationRequest request
    ) {
        TaskNotification notification = getNotification(notificationId);

        Task task = null;

        if (request.getTaskId() != null) {
            task = getTask(request.getTaskId());
        }

        Employee employee = getEmployee(request.getEmployeeId());

        notification.setNotificationType(request.getNotificationType());
        notification.setStatus(
                Optional.ofNullable(request.getStatus())
                        .orElse(NotificationStatus.UNREAD)
        );
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setTask(task);
        notification.setEmployee(employee);

        notification = taskNotificationRepository.save(notification);

        return mapToResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskNotificationResponse getNotificationById(
            Long notificationId
    ) {
        return mapToResponse(getNotification(notificationId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskNotificationResponse> getAllNotifications() {
        return taskNotificationRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskNotificationResponse> getNotificationsByEmployee(
            Long employeeId
    ) {
        Employee employee = getEmployee(employeeId);

        return taskNotificationRepository
                .findByEmployeeAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskNotificationResponse> getNotificationsByTask(
            Long taskId
    ) {
        Task task = getTask(taskId);

        return taskNotificationRepository
                .findByTaskAndDeletedFalse(task)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskNotificationResponse> getNotificationsByType(
            NotificationType notificationType
    ) {
        return taskNotificationRepository
                .findByNotificationTypeAndDeletedFalse(notificationType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskNotificationResponse> getNotificationsByStatus(
            NotificationStatus status
    ) {
        return taskNotificationRepository
                .findByStatusAndDeletedFalse(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskNotificationResponse> getNotificationsByEmployeeAndStatus(
            Long employeeId,
            NotificationStatus status
    ) {
        Employee employee = getEmployee(employeeId);

        return taskNotificationRepository
                .findByEmployeeAndStatusAndDeletedFalse(
                        employee,
                        status
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskNotificationResponse> getNotificationsByEmployeeAndType(
            Long employeeId,
            NotificationType notificationType
    ) {
        Employee employee = getEmployee(employeeId);

        return taskNotificationRepository
                .findByEmployeeAndNotificationTypeAndDeletedFalse(
                        employee,
                        notificationType
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskNotificationResponse> getNotificationsByTaskAndType(
            Long taskId,
            NotificationType notificationType
    ) {
        Task task = getTask(taskId);

        return taskNotificationRepository
                .findByTaskAndNotificationTypeAndDeletedFalse(
                        task,
                        notificationType
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEmployee(
            Long employeeId
    ) {
        Employee employee = getEmployee(employeeId);

        return taskNotificationRepository.countByEmployeeAndDeletedFalse(
                employee
        );
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEmployeeAndStatus(
            Long employeeId,
            NotificationStatus status
    ) {
        Employee employee = getEmployee(employeeId);

        return taskNotificationRepository
                .countByEmployeeAndStatusAndDeletedFalse(
                        employee,
                        status
                );
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTask(
            Long taskId
    ) {
        Task task = getTask(taskId);

        return taskNotificationRepository.countByTaskAndDeletedFalse(
                task
        );
    }

    @Override
    @Transactional(readOnly = true)
    public long countByType(
            NotificationType notificationType
    ) {
        return taskNotificationRepository
                .countByNotificationTypeAndDeletedFalse(
                        notificationType
                );
    }

    @Override
    public void markAsRead(
            Long notificationId
    ) {
        TaskNotification notification = getNotification(notificationId);

        notification.setStatus(NotificationStatus.READ);

        taskNotificationRepository.save(notification);
    }

    @Override
    public void markAsUnread(
            Long notificationId
    ) {
        TaskNotification notification = getNotification(notificationId);

        notification.setStatus(NotificationStatus.UNREAD);

        taskNotificationRepository.save(notification);
    }

    @Override
    public void activateNotification(
            Long notificationId
    ) {
        TaskNotification notification = getNotification(notificationId);

        notification.setActive(true);

        taskNotificationRepository.save(notification);
    }

    @Override
    public void deactivateNotification(
            Long notificationId
    ) {
        TaskNotification notification = getNotification(notificationId);

        notification.setActive(false);

        taskNotificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(
            Long notificationId
    ) {
        TaskNotification notification = getNotification(notificationId);

        notification.setDeleted(true);
        notification.setActive(false);

        taskNotificationRepository.save(notification);
    }
}