package com.company.taskportal.service;

import com.company.taskportal.dto.DepartmentRequest;
import com.company.taskportal.dto.DepartmentResponse;
import com.company.taskportal.entity.Department;
import com.company.taskportal.entity.Organization;
import com.company.taskportal.exception.DuplicateResourceException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.DepartmentRepository;
import com.company.taskportal.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final OrganizationRepository organizationRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 OrganizationRepository organizationRepository) {
        this.departmentRepository = departmentRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if (departmentRepository.existsByDepartmentCode(request.getDepartmentCode())) {
            throw new DuplicateResourceException("Department code already exists.");
        }

        if (departmentRepository.existsByOrganization_IdAndDepartmentName(
                request.getOrganizationId(),
                request.getDepartmentName())) {

            throw new DuplicateResourceException(
                    "Department name already exists in this organization.");
        }

        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        Department department = new Department();
        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());
        department.setOrganization(organization);

        return mapToResponse(departmentRepository.save(department));
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        return mapToResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());
        department.setOrganization(organization);

        return mapToResponse(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        department.setDeleted(true);

        departmentRepository.save(department);
    }

    @Override
    public void activateDepartment(Long id) {

        Department department = departmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        department.setActive(true);

        departmentRepository.save(department);
    }

    @Override
    public void deactivateDepartment(Long id) {

        Department department = departmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        department.setActive(false);

        departmentRepository.save(department);
    }

    private DepartmentResponse mapToResponse(Department department) {

        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());
        response.setDepartmentCode(department.getDepartmentCode());
        response.setDepartmentName(department.getDepartmentName());
        response.setDescription(department.getDescription());

        response.setOrganizationId(department.getOrganization().getId());
        response.setOrganizationName(
                department.getOrganization().getOrganizationName());

        response.setActive(department.getActive());

        return response;
    }
}