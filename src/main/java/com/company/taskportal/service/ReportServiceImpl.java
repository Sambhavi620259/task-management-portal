package com.company.taskportal.service;

import com.company.taskportal.dto.TaskReportResponse;
import com.company.taskportal.entity.Department;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Priority;
import com.company.taskportal.entity.Project;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskStatus;
import com.company.taskportal.repository.DepartmentRepository;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.ProjectRepository;
import com.company.taskportal.repository.TaskRepository;
import com.company.taskportal.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    private TaskReportResponse mapToResponse(Task task) {

        return TaskReportResponse.builder()
                .taskId(task.getId())
                .taskCode(task.getTaskCode())
                .taskName(task.getTaskName())

                .organization(task.getOrganization().getOrganizationName())
                .department(task.getDepartment().getDepartmentName())
                .project(task.getProject().getProjectName())
                .category(task.getTaskCategory().getCategoryName())

                .assignedEmployee(
                        task.getAssignedTo().getEmployeeCode()
                )

                .priority(task.getPriority())
                .status(task.getStatus())
                .taskType(task.getTaskType())

                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .completedDate(task.getCompletedDate())

                .estimatedHours(task.getEstimatedHours())
                .actualHours(task.getActualHours())

                .completionPercentage(
                        task.getCompletionPercentage()
                )

                .build();
    }

    @Override
    public List<TaskReportResponse> getTaskReport() {

        return taskRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TaskReportResponse> getTaskReportByProject(
            Long projectId
    ) {

        Project project = projectRepository.findByIdAndDeletedFalse(projectId)
                .orElseThrow(() ->
                        new RuntimeException("Project not found."));

        return taskRepository.findByProjectAndDeletedFalse(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TaskReportResponse> getTaskReportByDepartment(
            Long departmentId
    ) {

        Department department =
                departmentRepository.findByIdAndDeletedFalse(departmentId)
                        .orElseThrow(() ->
                                new RuntimeException("Department not found."));

        return taskRepository.findByDepartmentAndDeletedFalse(department)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }    @Override
    public List<TaskReportResponse> getTaskReportByEmployee(
            Long employeeId
    ) {

        Employee employee = employeeRepository
                .findByIdAndDeletedFalse(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found."));

        return taskRepository.findByAssignedToAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TaskReportResponse> getTaskReportByStatus(
            TaskStatus status
    ) {

        return taskRepository.findByStatusAndDeletedFalse(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TaskReportResponse> getTaskReportByPriority(
            Priority priority
    ) {

        return taskRepository.findByPriorityAndDeletedFalse(priority)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TaskReportResponse> getTaskReportByDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {

        return taskRepository.findByDeletedFalse()
                .stream()
                .filter(task ->
                        task.getStartDate() != null
                                && !task.getStartDate().isBefore(startDate)
                                && !task.getStartDate().isAfter(endDate)
                )
                .map(this::mapToResponse)
                .toList();
    }

}