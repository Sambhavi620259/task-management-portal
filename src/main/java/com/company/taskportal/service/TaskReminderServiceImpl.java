package com.company.taskportal.service;

import com.company.taskportal.dto.TaskReminderRequest;
import com.company.taskportal.dto.TaskReminderResponse;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.ReminderType;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskReminder;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.TaskReminderRepository;
import com.company.taskportal.repository.TaskRepository;
import com.company.taskportal.service.TaskReminderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskReminderServiceImpl implements TaskReminderService {

    private final TaskReminderRepository taskReminderRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    private TaskReminder getReminder(
            Long reminderId
    ) {
        return taskReminderRepository
                .findByIdAndDeletedFalse(reminderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task reminder not found with ID: " + reminderId
                        )
                );
    }

    private Task getTask(
            Long taskId
    ) {
        return taskRepository
                .findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with ID: " + taskId
                        )
                );
    }

    private Employee getEmployee(
            Long employeeId
    ) {
        return employeeRepository
                .findByIdAndDeletedFalse(employeeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + employeeId
                        )
                );
    }

    private TaskReminderResponse mapToResponse(
            TaskReminder reminder
    ) {
        Long taskId = null;
        String taskCode = null;
        String taskName = null;

        if (reminder.getTask() != null) {
            taskId = reminder.getTask().getId();
            taskCode = reminder.getTask().getTaskCode();
            taskName = reminder.getTask().getTaskName();
        }

        Long employeeId = null;
        String employeeCode = null;
        String employeeName = null;

        if (reminder.getEmployee() != null) {
            employeeId = reminder.getEmployee().getId();
            employeeCode = reminder.getEmployee().getEmployeeCode();
            employeeName=
                    reminder.getEmployee().getFirstName() + " " +
                            reminder.getEmployee().getLastName();

        }

        return TaskReminderResponse.builder()
                .id(
                        reminder.getId()
                )

                .taskId(
                        taskId
                )
                .taskCode(
                        taskCode
                )
                .taskName(
                        taskName
                )

                .employeeId(
                        employeeId
                )
                .employeeCode(
                        employeeCode
                )
                .employeeName(
                        employeeName
                )

                .reminderType(
                        reminder.getReminderType()
                )
                .reminderDateTime(
                        reminder.getReminderDateTime()
                )
                .message(
                        reminder.getMessage()
                )
                .sent(
                        reminder.getSent()
                )
                .sentAt(
                        reminder.getSentAt()
                )

                .active(
                        reminder.getActive()
                )
                .deleted(
                        reminder.getDeleted()
                )
                .createdAt(
                        reminder.getCreatedAt()
                )
                .updatedAt(
                        reminder.getUpdatedAt()
                )
                .build();
    }

    @Override
    public TaskReminderResponse createReminder(
            TaskReminderRequest request
    ) {
        Task task = getTask(request.getTaskId());

        Employee employee = getEmployee(request.getEmployeeId());

        if (taskReminderRepository
                .existsByTaskAndEmployeeAndReminderDateTimeAndDeletedFalse(
                        task,
                        employee,
                        request.getReminderDateTime()
                )) {
            throw new IllegalArgumentException(
                    "A reminder already exists for the specified task, employee and reminder time."
            );
        }

        TaskReminder reminder = TaskReminder.builder()
                .task(task)
                .employee(employee)
                .reminderType(request.getReminderType())
                .reminderDateTime(request.getReminderDateTime())
                .message(request.getMessage())
                .build();

        reminder = taskReminderRepository.save(reminder);

        return mapToResponse(reminder);
    }

    @Override
    public TaskReminderResponse updateReminder(
            Long reminderId,
            TaskReminderRequest request
    ) {
        TaskReminder reminder = getReminder(reminderId);

        Task task = getTask(request.getTaskId());

        Employee employee = getEmployee(request.getEmployeeId());

        List<TaskReminder> reminders =
                taskReminderRepository
                        .findByTaskAndEmployeeAndDeletedFalse(
                                task,
                                employee
                        );

        boolean duplicate = reminders.stream()
                .filter(r -> !r.getId().equals(reminderId))
                .anyMatch(r ->
                        r.getReminderDateTime()
                                .equals(request.getReminderDateTime())
                );

        if (duplicate) {
            throw new IllegalArgumentException(
                    "A reminder already exists for the specified task, employee and reminder time."
            );
        }

        reminder.setTask(task);
        reminder.setEmployee(employee);
        reminder.setReminderType(request.getReminderType());
        reminder.setReminderDateTime(request.getReminderDateTime());
        reminder.setMessage(request.getMessage());

        reminder = taskReminderRepository.save(reminder);

        return mapToResponse(reminder);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskReminderResponse getReminderById(
            Long reminderId
    ) {
        return mapToResponse(
                getReminder(reminderId)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getAllReminders() {
        return taskReminderRepository
                .findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersByTask(
            Long taskId
    ) {
        Task task = getTask(taskId);

        return taskReminderRepository
                .findByTaskAndDeletedFalse(task)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersByEmployee(
            Long employeeId
    ) {
        Employee employee = getEmployee(employeeId);

        return taskReminderRepository
                .findByEmployeeAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersByReminderType(
            ReminderType reminderType
    ) {
        return taskReminderRepository
                .findByReminderTypeAndDeletedFalse(reminderType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersByTaskAndReminderType(
            Long taskId,
            ReminderType reminderType
    ) {
        Task task = getTask(taskId);

        return taskReminderRepository
                .findByTaskAndReminderTypeAndDeletedFalse(
                        task,
                        reminderType
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersByEmployeeAndReminderType(
            Long employeeId,
            ReminderType reminderType
    ) {
        Employee employee = getEmployee(employeeId);

        return taskReminderRepository
                .findByEmployeeAndReminderTypeAndDeletedFalse(
                        employee,
                        reminderType
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersBySentStatus(
            Boolean sent
    ) {
        return taskReminderRepository
                .findBySentAndDeletedFalse(sent)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersByTaskAndSentStatus(
            Long taskId,
            Boolean sent
    ) {
        Task task = getTask(taskId);

        return taskReminderRepository
                .findByTaskAndSentAndDeletedFalse(
                        task,
                        sent
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersByEmployeeAndSentStatus(
            Long employeeId,
            Boolean sent
    ) {
        Employee employee = getEmployee(employeeId);

        return taskReminderRepository
                .findByEmployeeAndSentAndDeletedFalse(
                        employee,
                        sent
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getDueReminders() {
        return taskReminderRepository
                .findByReminderDateTimeLessThanEqualAndSentFalseAndDeletedFalse(
                        LocalDateTime.now()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskReminderResponse> getRemindersBetween(
            LocalDateTime start,
            LocalDateTime end
    ) {
        return taskReminderRepository
                .findByReminderDateTimeBetweenAndDeletedFalse(
                        start,
                        end
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTask(
            Long taskId
    ) {
        Task task = getTask(taskId);

        return taskReminderRepository
                .countByTaskAndDeletedFalse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEmployee(
            Long employeeId
    ) {
        Employee employee = getEmployee(employeeId);

        return taskReminderRepository
                .countByEmployeeAndDeletedFalse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByReminderType(
            ReminderType reminderType
    ) {
        return taskReminderRepository
                .countByReminderTypeAndDeletedFalse(
                        reminderType
                );
    }

    @Override
    @Transactional(readOnly = true)
    public long countBySentStatus(
            Boolean sent
    ) {
        return taskReminderRepository
                .countBySentAndDeletedFalse(sent);
    }

    @Override
    public void markAsSent(
            Long reminderId
    ) {
        TaskReminder reminder = getReminder(reminderId);

        reminder.setSent(true);
        reminder.setSentAt(LocalDateTime.now());

        taskReminderRepository.save(reminder);
    }

    @Override
    public void activateReminder(
            Long reminderId
    ) {
        TaskReminder reminder = getReminder(reminderId);

        reminder.setActive(true);

        taskReminderRepository.save(reminder);
    }

    @Override
    public void deactivateReminder(
            Long reminderId
    ) {
        TaskReminder reminder = getReminder(reminderId);

        reminder.setActive(false);

        taskReminderRepository.save(reminder);
    }

    @Override
    public void deleteReminder(
            Long reminderId
    ) {
        TaskReminder reminder = getReminder(reminderId);

        reminder.setDeleted(true);
        reminder.setActive(false);

        taskReminderRepository.save(reminder);
    }

}