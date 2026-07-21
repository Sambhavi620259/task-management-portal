package com.company.taskportal.service;

import com.company.taskportal.dto.ProjectRequest;
import com.company.taskportal.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {

    /**
     * Create a new project.
     */
    ProjectResponse createProject(ProjectRequest request);

    /**
     * Update an existing project.
     */
    ProjectResponse updateProject(Long id, ProjectRequest request);

    /**
     * Get project by ID.
     */
    ProjectResponse getProjectById(Long id);

    /**
     * Get all projects.
     */
    List<ProjectResponse> getAllProjects();

    /**
     * Get all active projects.
     */
    List<ProjectResponse> getAllActiveProjects();

    /**
     * Get all projects by organization.
     */
    List<ProjectResponse> getProjectsByOrganization(Long organizationId);

    /**
     * Activate a project.
     */
    void activateProject(Long id);

    /**
     * Deactivate a project.
     */
    void deactivateProject(Long id);

    /**
     * Soft delete a project.
     */
    void deleteProject(Long id);
}