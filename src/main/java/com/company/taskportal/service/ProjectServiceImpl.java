package com.company.taskportal.service;

import com.company.taskportal.dto.ProjectRequest;
import com.company.taskportal.dto.ProjectResponse;
import com.company.taskportal.entity.Organization;
import com.company.taskportal.entity.Project;
import com.company.taskportal.exception.DuplicateResourceException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.OrganizationRepository;
import com.company.taskportal.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        String projectCode = request.getProjectCode() != null ? request.getProjectCode().trim() : null;
        String projectName = request.getProjectName() != null ? request.getProjectName().trim() : null;
        String description = request.getDescription() != null ? request.getDescription().trim() : null;
        String projectManager = request.getProjectManager() != null ? request.getProjectManager().trim() : null;

        log.info("Creating project with code: {}", projectCode);

        if (request.getEndDate() != null && request.getStartDate() != null && request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        if (projectRepository.existsByProjectCodeAndDeletedFalse(projectCode)) {
            throw new DuplicateResourceException("Project code already exists.");
        }

        if (projectRepository.existsByProjectNameAndDeletedFalse(projectName)) {
            throw new DuplicateResourceException("Project name already exists.");
        }

        Organization organization = organizationRepository.findByIdAndDeletedFalse(request.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found or deleted with ID : "
                        + request.getOrganizationId()));

        if (!Boolean.TRUE.equals(organization.getActive())) {
            throw new IllegalArgumentException("Selected organization is inactive.");
        }

        Project project = Project.builder()
                .projectCode(projectCode)
                .projectName(projectName)
                .description(description)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .projectManager(projectManager)
                .organization(organization)
                .build();

        project.setActive(Boolean.TRUE);
        project.setDeleted(Boolean.FALSE);

        Project savedProject = projectRepository.save(project);
        log.info("Project {} created successfully with ID: {}", projectCode, savedProject.getId());

        return mapToResponse(savedProject);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        String projectCode = request.getProjectCode() != null ? request.getProjectCode().trim() : null;
        String projectName = request.getProjectName() != null ? request.getProjectName().trim() : null;
        String description = request.getDescription() != null ? request.getDescription().trim() : null;
        String projectManager = request.getProjectManager() != null ? request.getProjectManager().trim() : null;

        log.info("Updating project ID: {}", id);

        if (request.getEndDate() != null && request.getStartDate() != null && request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        Project project = projectRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID : " + id));

        if (projectRepository.existsByProjectCodeAndIdNotAndDeletedFalse(projectCode, id)) {
            throw new DuplicateResourceException("Project code already exists.");
        }

        if (projectRepository.existsByProjectNameAndIdNotAndDeletedFalse(projectName, id)) {
            throw new DuplicateResourceException("Project name already exists.");
        }

        Organization organization = organizationRepository.findByIdAndDeletedFalse(request.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found or deleted with ID : "
                        + request.getOrganizationId()));

        if (!Boolean.TRUE.equals(organization.getActive())) {
            throw new IllegalArgumentException("Selected organization is inactive.");
        }

        project.setProjectCode(projectCode);
        project.setProjectName(projectName);
        project.setDescription(description);
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setProjectManager(projectManager);
        project.setOrganization(organization);

        if (request.getActive() != null) {
            project.setActive(request.getActive());
        }

        Project updatedProject = projectRepository.save(project);
        log.info("Project ID: {} updated successfully", id);

        return mapToResponse(updatedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        log.debug("Fetching project by ID: {}", id);
        Project project = projectRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID : " + id));

        return mapToResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        log.debug("Fetching all non-deleted projects");
        return projectRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllActiveProjects() {
        log.debug("Fetching all active non-deleted projects");
        return projectRepository.findByActiveTrueAndDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjectsByOrganization(Long organizationId) {
        log.debug("Fetching projects for organization ID: {}", organizationId);
        return projectRepository.findByOrganizationIdAndDeletedFalse(organizationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void activateProject(Long id) {
        log.info("Activating project ID: {}", id);
        Project project = projectRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID : " + id));

        project.setActive(Boolean.TRUE);
        projectRepository.save(project);
    }

    @Override
    public void deactivateProject(Long id) {
        log.info("Deactivating project ID: {}", id);
        Project project = projectRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID : " + id));

        project.setActive(Boolean.FALSE);
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        log.info("Soft-deleting project ID: {}", id);
        Project project = projectRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID : " + id));

        project.setDeleted(Boolean.TRUE);
        project.setActive(Boolean.FALSE);
        projectRepository.save(project);
    }

    /**
     * Convert Project Entity to ProjectResponse DTO
     */
    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .projectCode(project.getProjectCode())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .projectManager(project.getProjectManager())
                .organizationId(project.getOrganization().getId())
                .organizationName(project.getOrganization().getOrganizationName())
                .active(project.getActive())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}