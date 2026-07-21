package com.company.taskportal.service;

import com.company.taskportal.dto.TaskCategoryRequest;
import com.company.taskportal.dto.TaskCategoryResponse;

import java.util.List;

public interface TaskCategoryService {

    TaskCategoryResponse createCategory(TaskCategoryRequest request);

    TaskCategoryResponse updateCategory(Long categoryId,
                                        TaskCategoryRequest request);

    TaskCategoryResponse getCategoryById(Long categoryId);

    TaskCategoryResponse getCategoryByCode(String categoryCode);

    List<TaskCategoryResponse> getAllCategories();

    List<TaskCategoryResponse> getAllActiveCategories();

    List<TaskCategoryResponse> getCategoriesByOrganization(Long organizationId);

    void activateCategory(Long categoryId);

    void deactivateCategory(Long categoryId);

    void deleteCategory(Long categoryId);
}