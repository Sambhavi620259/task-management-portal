package com.company.taskportal.service;

import com.company.taskportal.dto.TaskCategoryRequest;
import com.company.taskportal.dto.TaskCategoryResponse;
import com.company.taskportal.entity.Organization;
import com.company.taskportal.entity.TaskCategory;
import com.company.taskportal.exception.ResourceAlreadyExistsException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.OrganizationRepository;
import com.company.taskportal.repository.TaskCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskCategoryServiceImpl implements TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;
    private final OrganizationRepository organizationRepository;

    public TaskCategoryServiceImpl(TaskCategoryRepository taskCategoryRepository,
                                   OrganizationRepository organizationRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
        this.organizationRepository = organizationRepository;
    }

    private TaskCategoryResponse mapToResponse(TaskCategory category) {

        TaskCategoryResponse response = new TaskCategoryResponse();

        response.setId(category.getId());
        response.setCategoryCode(category.getCategoryCode());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());

        response.setOrganizationId(category.getOrganization().getId());
        response.setOrganizationName(category.getOrganization().getOrganizationName());

        response.setActive(category.getActive());
        response.setDeleted(category.getDeleted());

        response.setCreatedAt(category.getCreatedAt());
        response.setUpdatedAt(category.getUpdatedAt());

        return response;
    }
    @Override
    public TaskCategoryResponse createCategory(TaskCategoryRequest request) {

        if (taskCategoryRepository.existsByCategoryCodeAndDeletedFalse(request.getCategoryCode())) {
            throw new ResourceAlreadyExistsException(
                    "Task Category code already exists: " + request.getCategoryCode());
        }

        if (taskCategoryRepository.existsByCategoryNameAndDeletedFalse(request.getCategoryName())) {
            throw new ResourceAlreadyExistsException(
                    "Task Category name already exists: " + request.getCategoryName());
        }

        Organization organization = organizationRepository
                .findByIdAndDeletedFalse(request.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with ID: " + request.getOrganizationId()));

        TaskCategory category = new TaskCategory();

        category.setCategoryCode(request.getCategoryCode());
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setOrganization(organization);

        category.setActive(true);
        category.setDeleted(false);

        TaskCategory savedCategory = taskCategoryRepository.save(category);

        return mapToResponse(savedCategory);
    }
    @Override
    public TaskCategoryResponse updateCategory(Long categoryId,
                                               TaskCategoryRequest request) {

        TaskCategory category = taskCategoryRepository
                .findByIdAndDeletedFalse(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task Category not found with ID: " + categoryId));

        if (!category.getCategoryCode().equalsIgnoreCase(request.getCategoryCode())
                && taskCategoryRepository.existsByCategoryCodeAndDeletedFalse(request.getCategoryCode())) {

            throw new ResourceAlreadyExistsException(
                    "Task Category code already exists: " + request.getCategoryCode());
        }

        if (!category.getCategoryName().equalsIgnoreCase(request.getCategoryName())
                && taskCategoryRepository.existsByCategoryNameAndDeletedFalse(request.getCategoryName())) {

            throw new ResourceAlreadyExistsException(
                    "Task Category name already exists: " + request.getCategoryName());
        }

        Organization organization = organizationRepository
                .findByIdAndDeletedFalse(request.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with ID: " + request.getOrganizationId()));

        category.setCategoryCode(request.getCategoryCode());
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setOrganization(organization);

        TaskCategory updatedCategory = taskCategoryRepository.save(category);

        return mapToResponse(updatedCategory);
    }
    @Override
    @Transactional(readOnly = true)
    public TaskCategoryResponse getCategoryById(Long categoryId) {

        TaskCategory category = taskCategoryRepository
                .findByIdAndDeletedFalse(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task Category not found with ID: " + categoryId));

        return mapToResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskCategoryResponse getCategoryByCode(String categoryCode) {

        TaskCategory category = taskCategoryRepository
                .findByCategoryCodeAndDeletedFalse(categoryCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task Category not found with code: " + categoryCode));

        return mapToResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCategoryResponse> getAllCategories() {

        return taskCategoryRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCategoryResponse> getAllActiveCategories() {

        return taskCategoryRepository.findByActiveTrueAndDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskCategoryResponse> getCategoriesByOrganization(Long organizationId) {

        Organization organization = organizationRepository
                .findByIdAndDeletedFalse(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organization not found with ID: " + organizationId));

        return taskCategoryRepository.findByOrganizationAndDeletedFalse(organization)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public void activateCategory(Long categoryId) {

        TaskCategory category = taskCategoryRepository
                .findByIdAndDeletedFalse(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task Category not found with ID: " + categoryId));

        category.setActive(true);

        taskCategoryRepository.save(category);
    }

    @Override
    public void deactivateCategory(Long categoryId) {

        TaskCategory category = taskCategoryRepository
                .findByIdAndDeletedFalse(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task Category not found with ID: " + categoryId));

        category.setActive(false);

        taskCategoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        TaskCategory category = taskCategoryRepository
                .findByIdAndDeletedFalse(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task Category not found with ID: " + categoryId));

        category.setDeleted(true);
        category.setActive(false);

        taskCategoryRepository.save(category);
    }
}