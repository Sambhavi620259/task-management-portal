package com.company.taskportal.service;

import com.company.taskportal.dto.TaskChecklistRequest;
import com.company.taskportal.dto.TaskChecklistResponse;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskChecklist;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.TaskChecklistRepository;
import com.company.taskportal.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskChecklistServiceImpl implements TaskChecklistService {

    private final TaskChecklistRepository taskChecklistRepository;
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    private Task getTask(Long id) {
        return taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task not found with id : " + id));
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id : " + id));
    }

    private TaskChecklist getChecklist(Long id) {
        return taskChecklistRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Checklist not found with id : " + id));
    }

    private TaskChecklist mapToEntity(TaskChecklistRequest request) {

        TaskChecklist checklist = new TaskChecklist();

        checklist.setTitle(request.getTitle());
        checklist.setDescription(request.getDescription());

        checklist.setTask(getTask(request.getTaskId()));

        if (request.getAssignedToId() != null) {
            checklist.setAssignedTo(getEmployee(request.getAssignedToId()));
        }

        checklist.setCompleted(
                request.getCompleted() != null
                        ? request.getCompleted()
                        : Boolean.FALSE
        );

        return checklist;
    }

    private TaskChecklistResponse mapToResponse(TaskChecklist checklist) {

        TaskChecklistResponse response = new TaskChecklistResponse();

        response.setId(checklist.getId());

        // Task Information
        if (checklist.getTask() != null) {
            response.setTaskId(checklist.getTask().getId());
            response.setTaskCode(checklist.getTask().getTaskCode());
            response.setTaskName(checklist.getTask().getTaskName());
        }

        // Checklist Information
        response.setTitle(checklist.getTitle());
        response.setDescription(checklist.getDescription());
        response.setCompleted(checklist.getCompleted());

        // Assigned Employee Information
        if (checklist.getAssignedTo() != null) {

            Employee employee = checklist.getAssignedTo();

            response.setAssignedToId(employee.getId());
            response.setEmployeeCode(employee.getEmployeeCode());

            String fullName = Stream.of(
                            employee.getFirstName(),
                            employee.getLastName()
                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "))
                    .trim();

            response.setEmployeeName(fullName);
        }

        // BaseEntity Information
        response.setActive(checklist.getActive());
        response.setDeleted(checklist.getDeleted());
        response.setCreatedAt(checklist.getCreatedAt());
        response.setUpdatedAt(checklist.getUpdatedAt());

        return response;
    }    @Override
    public TaskChecklistResponse createChecklist(TaskChecklistRequest request) {

        TaskChecklist checklist = mapToEntity(request);

        checklist = taskChecklistRepository.save(checklist);

        return mapToResponse(checklist);
    }

    @Override
    public TaskChecklistResponse updateChecklist(
            Long checklistId,
            TaskChecklistRequest request) {

        TaskChecklist checklist = getChecklist(checklistId);

        checklist.setTitle(request.getTitle());
        checklist.setDescription(request.getDescription());

        checklist.setTask(getTask(request.getTaskId()));

        if (request.getAssignedToId() != null) {
            checklist.setAssignedTo(getEmployee(request.getAssignedToId()));
        } else {
            checklist.setAssignedTo(null);
        }

        checklist.setCompleted(
                request.getCompleted() != null
                        ? request.getCompleted()
                        : Boolean.FALSE
        );

        checklist = taskChecklistRepository.save(checklist);

        return mapToResponse(checklist);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskChecklistResponse getChecklistById(Long checklistId) {

        return mapToResponse(getChecklist(checklistId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getAllChecklists() {

        return taskChecklistRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getChecklistsByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskChecklistRepository.findByTaskAndDeletedFalse(task)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getChecklistsByAssignedEmployee(Long employeeId) {

        Employee employee = getEmployee(employeeId);

        return taskChecklistRepository.findByAssignedToAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getCompletedChecklists() {

        return taskChecklistRepository.findByCompletedAndDeletedFalse(true)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getPendingChecklists() {

        return taskChecklistRepository.findByCompletedAndDeletedFalse(false)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getCompletedChecklistsByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskChecklistRepository
                .findByTaskAndCompletedAndDeletedFalse(task, true)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getPendingChecklistsByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskChecklistRepository
                .findByTaskAndCompletedAndDeletedFalse(task, false)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getCompletedChecklistsByEmployee(Long employeeId) {

        Employee employee = getEmployee(employeeId);

        return taskChecklistRepository
                .findByAssignedToAndCompletedAndDeletedFalse(employee, true)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskChecklistResponse> getPendingChecklistsByEmployee(Long employeeId) {

        Employee employee = getEmployee(employeeId);

        return taskChecklistRepository
                .findByAssignedToAndCompletedAndDeletedFalse(employee, false)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }    @Override
    @Transactional(readOnly = true)
    public long countByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskChecklistRepository.countByTaskAndDeletedFalse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCompletedByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskChecklistRepository.countByTaskAndCompletedAndDeletedFalse(
                task,
                true
        );
    }

    @Override
    @Transactional(readOnly = true)
    public long countPendingByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskChecklistRepository.countByTaskAndCompletedAndDeletedFalse(
                task,
                false
        );
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEmployee(Long employeeId) {

        Employee employee = getEmployee(employeeId);

        return taskChecklistRepository.countByAssignedToAndDeletedFalse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCompletedByEmployee(Long employeeId) {

        Employee employee = getEmployee(employeeId);

        return taskChecklistRepository
                .countByAssignedToAndCompletedAndDeletedFalse(
                        employee,
                        true
                );
    }

    @Override
    @Transactional(readOnly = true)
    public long countPendingByEmployee(Long employeeId) {

        Employee employee = getEmployee(employeeId);

        return taskChecklistRepository
                .countByAssignedToAndCompletedAndDeletedFalse(
                        employee,
                        false
                );
    }

    @Override
    public TaskChecklistResponse markCompleted(Long checklistId) {

        TaskChecklist checklist = getChecklist(checklistId);

        checklist.setCompleted(true);

        checklist = taskChecklistRepository.save(checklist);

        return mapToResponse(checklist);
    }

    @Override
    public TaskChecklistResponse markPending(Long checklistId) {

        TaskChecklist checklist = getChecklist(checklistId);

        checklist.setCompleted(false);

        checklist = taskChecklistRepository.save(checklist);

        return mapToResponse(checklist);
    }    @Override
    public void activateChecklist(Long checklistId) {

        TaskChecklist checklist = getChecklist(checklistId);

        checklist.setActive(true);

        taskChecklistRepository.save(checklist);
    }

    @Override
    public void deactivateChecklist(Long checklistId) {

        TaskChecklist checklist = getChecklist(checklistId);

        checklist.setActive(false);

        taskChecklistRepository.save(checklist);
    }

    @Override
    public void deleteChecklist(Long checklistId) {

        TaskChecklist checklist = getChecklist(checklistId);

        checklist.setDeleted(true);
        checklist.setActive(false);

        taskChecklistRepository.save(checklist);
    }

}