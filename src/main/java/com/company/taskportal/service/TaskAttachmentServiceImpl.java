package com.company.taskportal.service;

import com.company.taskportal.dto.TaskAttachmentRequest;
import com.company.taskportal.dto.TaskAttachmentResponse;
import com.company.taskportal.entity.AttachmentType;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Task;
import com.company.taskportal.entity.TaskAttachment;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.TaskAttachmentRepository;
import com.company.taskportal.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskAttachmentServiceImpl implements TaskAttachmentService {

    private final TaskAttachmentRepository taskAttachmentRepository;
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

    private TaskAttachment mapToEntity(TaskAttachmentRequest request) {

        TaskAttachment attachment = new TaskAttachment();

        attachment.setTask(getTask(request.getTaskId()));
        attachment.setUploadedBy(getEmployee(request.getUploadedById()));

        attachment.setFileName(request.getFileName());
        attachment.setOriginalFileName(request.getOriginalFileName());
        attachment.setFileUrl(request.getFileUrl());
        attachment.setContentType(request.getContentType());
        attachment.setFileSize(request.getFileSize());
        attachment.setAttachmentType(request.getAttachmentType());

        return attachment;
    }

    private TaskAttachmentResponse mapToResponse(TaskAttachment attachment) {

        TaskAttachmentResponse response = new TaskAttachmentResponse();

        response.setId(attachment.getId());

        // Task
        if (attachment.getTask() != null) {
            response.setTaskId(attachment.getTask().getId());
            response.setTaskCode(attachment.getTask().getTaskCode());
            response.setTaskName(attachment.getTask().getTaskName());
        }

        // Uploaded By
        if (attachment.getUploadedBy() != null) {
            response.setUploadedById(attachment.getUploadedBy().getId());
            response.setEmployeeCode(
                    attachment.getUploadedBy().getEmployeeCode());
            response.setEmployeeName(
                    attachment.getUploadedBy().getFirstName()
                            + " "
                            + attachment.getUploadedBy().getLastName());
        }

        // File Information
        response.setFileName(attachment.getFileName());
        response.setOriginalFileName(attachment.getOriginalFileName());
        response.setFileUrl(attachment.getFileUrl());
        response.setContentType(attachment.getContentType());
        response.setFileSize(attachment.getFileSize());
        response.setAttachmentType(attachment.getAttachmentType());

        // Audit
        response.setActive(attachment.getActive());
        response.setDeleted(attachment.getDeleted());
        response.setCreatedAt(attachment.getCreatedAt());
        response.setUpdatedAt(attachment.getUpdatedAt());

        return response;
    }
    @Override
    public TaskAttachmentResponse createAttachment(TaskAttachmentRequest request) {

        TaskAttachment attachment = mapToEntity(request);

        attachment = taskAttachmentRepository.save(attachment);

        return mapToResponse(attachment);
    }

    @Override
    public TaskAttachmentResponse updateAttachment(
            Long attachmentId,
            TaskAttachmentRequest request) {

        TaskAttachment attachment = taskAttachmentRepository
                .findByIdAndDeletedFalse(attachmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task attachment not found with id : " + attachmentId));

        attachment.setTask(getTask(request.getTaskId()));
        attachment.setUploadedBy(getEmployee(request.getUploadedById()));

        attachment.setFileName(request.getFileName());
        attachment.setOriginalFileName(request.getOriginalFileName());
        attachment.setFileUrl(request.getFileUrl());
        attachment.setContentType(request.getContentType());
        attachment.setFileSize(request.getFileSize());
        attachment.setAttachmentType(request.getAttachmentType());

        attachment = taskAttachmentRepository.save(attachment);

        return mapToResponse(attachment);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskAttachmentResponse getAttachmentById(Long attachmentId) {

        TaskAttachment attachment = taskAttachmentRepository
                .findByIdAndDeletedFalse(attachmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task attachment not found with id : " + attachmentId));

        return mapToResponse(attachment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskAttachmentResponse> getAllAttachments() {

        return taskAttachmentRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<TaskAttachmentResponse> getAttachmentsByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskAttachmentRepository.findByTaskAndDeletedFalse(task)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskAttachmentResponse> getAttachmentsByUploadedBy(Long uploadedById) {

        Employee employee = getEmployee(uploadedById);

        return taskAttachmentRepository.findByUploadedByAndDeletedFalse(employee)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskAttachmentResponse> getAttachmentsByAttachmentType(
            AttachmentType attachmentType) {

        return taskAttachmentRepository
                .findByAttachmentTypeAndDeletedFalse(attachmentType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskAttachmentResponse> getAttachmentsByTaskAndAttachmentType(
            Long taskId,
            AttachmentType attachmentType) {

        Task task = getTask(taskId);

        return taskAttachmentRepository
                .findByTaskAndAttachmentTypeAndDeletedFalse(task, attachmentType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public long countAttachmentsByTask(Long taskId) {

        Task task = getTask(taskId);

        return taskAttachmentRepository.countByTaskAndDeletedFalse(task);
    }

    @Override
    @Transactional(readOnly = true)
    public long countAttachmentsByUploadedBy(Long uploadedById) {

        Employee employee = getEmployee(uploadedById);

        return taskAttachmentRepository.countByUploadedByAndDeletedFalse(employee);
    }

    @Override
    public void activateAttachment(Long attachmentId) {

        TaskAttachment attachment = taskAttachmentRepository
                .findByIdAndDeletedFalse(attachmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task attachment not found with id : " + attachmentId));

        attachment.setActive(true);

        taskAttachmentRepository.save(attachment);
    }

    @Override
    public void deactivateAttachment(Long attachmentId) {

        TaskAttachment attachment = taskAttachmentRepository
                .findByIdAndDeletedFalse(attachmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task attachment not found with id : " + attachmentId));

        attachment.setActive(false);

        taskAttachmentRepository.save(attachment);
    }

    @Override
    public void deleteAttachment(Long attachmentId) {

        TaskAttachment attachment = taskAttachmentRepository
                .findByIdAndDeletedFalse(attachmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Task attachment not found with id : " + attachmentId));

        attachment.setDeleted(true);
        attachment.setActive(false);

        taskAttachmentRepository.save(attachment);
    }

}