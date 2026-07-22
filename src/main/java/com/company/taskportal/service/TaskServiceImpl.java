package com.company.taskportal.service;

import com.company.taskportal.dto.TaskRequest;
import com.company.taskportal.dto.TaskResponse;
import com.company.taskportal.entity.*;
import com.company.taskportal.exception.ResourceAlreadyExistsException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.*;
import com.company.taskportal.service.EmailService;
import com.company.taskportal.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final FrequencyRepository frequencyRepository;
    private final EmailService emailService;

    private Organization getOrganization(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found with id: " + id));
    }

    private Department getDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: " + id));
    }

    private Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + id));
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));
    }

    private TaskCategory getTaskCategory(Long id) {
        return taskCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task Category not found with id: " + id));
    }

    private Frequency getFrequency(Long id) {
        if (id == null) {
            return null;
        }

        return frequencyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Frequency not found with id: " + id));
    }

    private Task mapToEntity(TaskRequest request) {
        Task task = new Task();

        task.setTaskCode(request.getTaskCode());
        task.setTaskName(request.getTaskName());
        task.setDescription(request.getDescription());

        task.setOrganization(getOrganization(request.getOrganizationId()));
        task.setDepartment(getDepartment(request.getDepartmentId()));
        task.setProject(getProject(request.getProjectId()));
        task.setTaskCategory(getTaskCategory(request.getTaskCategoryId()));
        task.setFrequency(getFrequency(request.getFrequencyId()));

        task.setAssignedTo(getEmployee(request.getAssignedToId()));

        if (request.getReviewedById() != null) {
            task.setReviewedBy(getEmployee(request.getReviewedById()));
        }

        if (request.getApprovedById() != null) {
            task.setApprovedBy(getEmployee(request.getApprovedById()));
        }

        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setTaskType(request.getTaskType());

        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());

        task.setEstimatedHours(request.getEstimatedHours());
        task.setActualHours(request.getActualHours());

        task.setCompletionPercentage(
                request.getCompletionPercentage() == null ? 0 : request.getCompletionPercentage()
        );

        task.setWorkflowRequired(
                request.getWorkflowRequired() == null ? false : request.getWorkflowRequired()
        );

        task.setApprovalRequired(
                request.getApprovalRequired() == null ? false : request.getApprovalRequired()
        );

        task.setWorkflowStep(request.getWorkflowStep());

        task.setSlaHours(request.getSlaHours());
        task.setReminderBeforeHours(request.getReminderBeforeHours());
        task.setEscalationAfterHours(request.getEscalationAfterHours());

        task.setEmailNotification(
                request.getEmailNotification() == null ? true : request.getEmailNotification()
        );

        task.setSmsNotification(
                request.getSmsNotification() == null ? false : request.getSmsNotification()
        );

        task.setPushNotification(
                request.getPushNotification() == null ? true : request.getPushNotification()
        );

        task.setNextExecutionDate(request.getNextExecutionDate());
        task.setLastExecutionDate(request.getLastExecutionDate());

        task.setAutoGenerateNext(
                request.getAutoGenerateNext() == null ? false : request.getAutoGenerateNext()
        );

        return task;
    }

    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();

        response.setId(task.getId());

        response.setTaskCode(task.getTaskCode());
        response.setTaskName(task.getTaskName());
        response.setDescription(task.getDescription());

        // Organization
        if (task.getOrganization() != null) {
            response.setOrganizationId(task.getOrganization().getId());
            response.setOrganizationName(task.getOrganization().getOrganizationName());
        }

        // Department
        if (task.getDepartment() != null) {
            response.setDepartmentId(task.getDepartment().getId());
            response.setDepartmentName(task.getDepartment().getDepartmentName());
        }

        // Project
        if (task.getProject() != null) {
            response.setProjectId(task.getProject().getId());
            response.setProjectName(task.getProject().getProjectName());
        }

        // Category
        if (task.getTaskCategory() != null) {
            response.setTaskCategoryId(task.getTaskCategory().getId());
            response.setTaskCategoryName(task.getTaskCategory().getCategoryName());
        }

        // Frequency
        if (task.getFrequency() != null) {
            response.setFrequencyId(task.getFrequency().getId());
            response.setFrequencyName(task.getFrequency().getFrequencyName());
        }

        // Assigned Employee
        if (task.getAssignedTo() != null) {
            response.setAssignedToId(task.getAssignedTo().getId());
            response.setAssignedToName(task.getAssignedTo().getFirstName() + " " + task.getAssignedTo().getLastName());
        }

        // Created By
        if (task.getCreatedBy() != null) {
            response.setCreatedById(task.getCreatedBy().getId());
            response.setCreatedByName(
                    task.getCreatedBy().getFirstName() + " " + task.getCreatedBy().getLastName()
            );
        }

        // Reviewed By
        if (task.getReviewedBy() != null) {
            response.setReviewedById(task.getReviewedBy().getId());
            response.setReviewedByName(
                    task.getReviewedBy().getFirstName() + " " + task.getReviewedBy().getLastName()
            );
        }

        // Approved By
        if (task.getApprovedBy() != null) {
            response.setApprovedById(task.getApprovedBy().getId());
            response.setApprovedByName(
                    task.getApprovedBy().getFirstName() + " " + task.getApprovedBy().getLastName()
            );
        }

        response.setPriority(task.getPriority());
        response.setStatus(task.getStatus());
        response.setTaskType(task.getTaskType());

        response.setStartDate(task.getStartDate());
        response.setDueDate(task.getDueDate());
        response.setCompletedDate(task.getCompletedDate());

        response.setEstimatedHours(task.getEstimatedHours());
        response.setActualHours(task.getActualHours());

        response.setWorkflowRequired(task.getWorkflowRequired());
        response.setApprovalRequired(task.getApprovalRequired());
        response.setWorkflowStep(task.getWorkflowStep());

        response.setCompletionPercentage(task.getCompletionPercentage());

        response.setSlaHours(task.getSlaHours());
        response.setReminderBeforeHours(task.getReminderBeforeHours());
        response.setEscalationAfterHours(task.getEscalationAfterHours());

        response.setEmailNotification(task.getEmailNotification());
        response.setSmsNotification(task.getSmsNotification());
        response.setPushNotification(task.getPushNotification());

        response.setNextExecutionDate(task.getNextExecutionDate());
        response.setLastExecutionDate(task.getLastExecutionDate());

        response.setAutoGenerateNext(task.getAutoGenerateNext());

        response.setActive(task.getActive());
        response.setDeleted(task.getDeleted());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());

        return response;
    }

    @Override
    public TaskResponse createTask(TaskRequest request) {
        if (taskRepository.existsByTaskCodeAndDeletedFalse(request.getTaskCode())) {
            throw new ResourceAlreadyExistsException(
                    "Task already exists with code : " + request.getTaskCode());
        }

        Task task = mapToEntity(request);
        Task savedTask = taskRepository.save(task);

        if (Boolean.TRUE.equals(savedTask.getEmailNotification())) {
            emailService.sendTaskAssignmentEmail(savedTask);
        }

        return mapToResponse(savedTask);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + id));

        if (!task.getTaskCode().equals(request.getTaskCode())
                && taskRepository.existsByTaskCodeAndDeletedFalse(request.getTaskCode())) {
            throw new ResourceAlreadyExistsException(
                    "Task already exists with code : " + request.getTaskCode());
        }

        task.setTaskCode(request.getTaskCode());
        task.setTaskName(request.getTaskName());
        task.setDescription(request.getDescription());

        task.setOrganization(getOrganization(request.getOrganizationId()));
        task.setDepartment(getDepartment(request.getDepartmentId()));
        task.setProject(getProject(request.getProjectId()));
        task.setTaskCategory(getTaskCategory(request.getTaskCategoryId()));
        task.setFrequency(getFrequency(request.getFrequencyId()));

        task.setAssignedTo(getEmployee(request.getAssignedToId()));

        task.setReviewedBy(
                request.getReviewedById() == null
                        ? null
                        : getEmployee(request.getReviewedById()));

        task.setApprovedBy(
                request.getApprovedById() == null
                        ? null
                        : getEmployee(request.getApprovedById()));

        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setTaskType(request.getTaskType());

        task.setStartDate(request.getStartDate());
        task.setDueDate(request.getDueDate());

        task.setEstimatedHours(request.getEstimatedHours());
        task.setActualHours(request.getActualHours());

        task.setCompletionPercentage(request.getCompletionPercentage());

        task.setWorkflowRequired(request.getWorkflowRequired());
        task.setApprovalRequired(request.getApprovalRequired());
        task.setWorkflowStep(request.getWorkflowStep());

        task.setSlaHours(request.getSlaHours());
        task.setReminderBeforeHours(request.getReminderBeforeHours());
        task.setEscalationAfterHours(request.getEscalationAfterHours());

        task.setEmailNotification(request.getEmailNotification());
        task.setSmsNotification(request.getSmsNotification());
        task.setPushNotification(request.getPushNotification());

        task.setNextExecutionDate(request.getNextExecutionDate());
        task.setLastExecutionDate(request.getLastExecutionDate());
        task.setAutoGenerateNext(request.getAutoGenerateNext());

        Task updatedTask = taskRepository.save(task);

        return mapToResponse(updatedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + id));

        return mapToResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskByCode(String taskCode) {
        Task task = taskRepository.findByTaskCodeAndDeletedFalse(taskCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with code : " + taskCode));

        return mapToResponse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByOrganization(Long organizationId) {
        Organization organization = getOrganization(organizationId);

        return taskRepository.findByOrganizationAndDeletedFalse(organization)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByDepartment(Long departmentId) {
        Department department = getDepartment(departmentId);

        return taskRepository.findByDepartmentAndDeletedFalse(department)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByProject(Long projectId) {
        Project project = getProject(projectId);

        return taskRepository.findByProjectAndDeletedFalse(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByCategory(Long categoryId) {
        TaskCategory category = getTaskCategory(categoryId);

        return taskRepository.findByTaskCategoryAndDeletedFalse(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByAssignedEmployee(Long employeeId) {
        Employee employee = getEmployee(employeeId);

        return taskRepository.findByAssignedToAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatusAndDeletedFalse(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriorityAndDeletedFalse(priority)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getOverdueTasks() {
        return taskRepository
                .findByDueDateBeforeAndStatusNotAndDeletedFalse(
                        java.time.LocalDate.now(),
                        TaskStatus.COMPLETED
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TaskResponse updateProgress(Long taskId, Integer completionPercentage) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        if (completionPercentage < 0 || completionPercentage > 100) {
            throw new IllegalArgumentException(
                    "Completion percentage must be between 0 and 100");
        }

        task.setCompletionPercentage(completionPercentage);

        if (completionPercentage == 100) {
            task.setStatus(TaskStatus.COMPLETED);
            task.setCompletedDate(java.time.LocalDate.now());
        } else if (task.getStatus() == TaskStatus.OPEN
                || task.getStatus() == TaskStatus.ASSIGNED) {
            task.setStatus(TaskStatus.IN_PROGRESS);
        }

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponse completeTask(Long taskId) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletionPercentage(100);
        task.setCompletedDate(java.time.LocalDate.now());

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponse approveTask(Long taskId) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        task.setStatus(TaskStatus.APPROVED);

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public TaskResponse rejectTask(Long taskId) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        task.setStatus(TaskStatus.REJECTED);

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public void activateTask(Long taskId) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        task.setActive(true);

        taskRepository.save(task);
    }

    @Override
    public void deactivateTask(Long taskId) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        task.setActive(false);

        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        task.setDeleted(true);
        task.setActive(false);

        taskRepository.save(task);
    }
    @Override
    public TaskResponse updateProgress(Long taskId, Integer completionPercentage) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        if (completionPercentage < 0 || completionPercentage > 100) {
            throw new IllegalArgumentException(
                    "Completion percentage must be between 0 and 100");
        }

        task.setCompletionPercentage(completionPercentage);

        if (completionPercentage == 100) {
            task.setStatus(TaskStatus.COMPLETED);
            task.setCompletedDate(java.time.LocalDate.now());
        } else if (task.getStatus() == TaskStatus.OPEN
                || task.getStatus() == TaskStatus.ASSIGNED) {
            task.setStatus(TaskStatus.IN_PROGRESS);
        }

        Task savedTask = taskRepository.save(task);

        // Trigger completion email if applicable
        if (savedTask.getStatus() == TaskStatus.COMPLETED
                && Boolean.TRUE.equals(savedTask.getEmailNotification())) {
            emailService.sendTaskCompletionEmail(savedTask);
        }

        return mapToResponse(savedTask);
    }
    @Override
    public TaskResponse completeTask(Long taskId) {
        Task task = taskRepository.findByIdAndDeletedFalse(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id : " + taskId));

        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletionPercentage(100);
        task.setCompletedDate(java.time.LocalDate.now());

        Task savedTask = taskRepository.save(task);

        // Trigger completion email if applicable
        if (savedTask.getStatus() == TaskStatus.COMPLETED
                && Boolean.TRUE.equals(savedTask.getEmailNotification())) {
            emailService.sendTaskCompletionEmail(savedTask);
        }

        return mapToResponse(savedTask);
    }
}