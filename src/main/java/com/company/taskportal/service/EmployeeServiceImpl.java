package com.company.taskportal.service;

import com.company.taskportal.dto.EmployeeRequest;
import com.company.taskportal.dto.EmployeeResponse;
import com.company.taskportal.entity.Department;
import com.company.taskportal.entity.Employee;
import com.company.taskportal.entity.Organization;
import com.company.taskportal.exception.DuplicateResourceException;
import com.company.taskportal.exception.ResourceNotFoundException;
import com.company.taskportal.repository.DepartmentRepository;
import com.company.taskportal.repository.EmployeeRepository;
import com.company.taskportal.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        String trimmedCode = request.getEmployeeCode() != null ? request.getEmployeeCode().trim() : null;
        String normalizedEmail = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : null;

        log.info("Creating employee with code: {}", trimmedCode);

        if (employeeRepository.existsByEmployeeCodeAndDeletedFalse(trimmedCode)) {
            throw new DuplicateResourceException("Employee code already exists.");
        }

        if (employeeRepository.existsByEmailAndDeletedFalse(normalizedEmail)) {
            throw new DuplicateResourceException("Email already exists.");
        }

        Organization organization = organizationRepository.findByIdAndDeletedFalse(request.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found or deleted with ID : "
                        + request.getOrganizationId()));

        if (!Boolean.TRUE.equals(organization.getActive())) {
            throw new IllegalArgumentException("Selected organization is inactive.");
        }

        Department department = departmentRepository.findByIdAndDeletedFalse(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found or deleted with ID : "
                        + request.getDepartmentId()));

        if (!Boolean.TRUE.equals(department.getActive())) {
            throw new IllegalArgumentException("Selected department is inactive.");
        }

        if (!department.getOrganization().getId().equals(organization.getId())) {
            throw new IllegalArgumentException("Selected department does not belong to the selected organization.");
        }

        Employee employee = Employee.builder()
                .employeeCode(trimmedCode)
                .firstName(request.getFirstName() != null ? request.getFirstName().trim() : null)
                .lastName(request.getLastName() != null ? request.getLastName().trim() : null)
                .email(normalizedEmail)
                .mobileNumber(request.getMobileNumber() != null ? request.getMobileNumber().trim() : null)
                .designation(request.getDesignation() != null ? request.getDesignation().trim() : null)
                .joiningDate(request.getJoiningDate())
                .organization(organization)
                .department(department)
                .build();

        employee.setActive(Boolean.TRUE);
        employee.setDeleted(Boolean.FALSE);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee {} created successfully with ID: {}", trimmedCode, savedEmployee.getId());

        return mapToResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        String trimmedCode = request.getEmployeeCode() != null ? request.getEmployeeCode().trim() : null;
        String normalizedEmail = request.getEmail() != null ? request.getEmail().trim().toLowerCase() : null;

        log.info("Updating employee ID: {}", id);

        Employee employee = employeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID : " + id));

        if (employeeRepository.existsByEmployeeCodeAndIdNotAndDeletedFalse(trimmedCode, id)) {
            throw new DuplicateResourceException("Employee code already exists.");
        }

        if (employeeRepository.existsByEmailAndIdNotAndDeletedFalse(normalizedEmail, id)) {
            throw new DuplicateResourceException("Email already exists.");
        }

        Organization organization = organizationRepository.findByIdAndDeletedFalse(request.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found or deleted with ID : "
                        + request.getOrganizationId()));

        if (!Boolean.TRUE.equals(organization.getActive())) {
            throw new IllegalArgumentException("Selected organization is inactive.");
        }

        Department department = departmentRepository.findByIdAndDeletedFalse(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found or deleted with ID : "
                        + request.getDepartmentId()));

        if (!Boolean.TRUE.equals(department.getActive())) {
            throw new IllegalArgumentException("Selected department is inactive.");
        }

        if (!department.getOrganization().getId().equals(organization.getId())) {
            throw new IllegalArgumentException("Selected department does not belong to the selected organization.");
        }

        employee.setEmployeeCode(trimmedCode);
        employee.setFirstName(request.getFirstName() != null ? request.getFirstName().trim() : null) ;
        employee.setLastName(request.getLastName() != null ? request.getLastName().trim() : null);
        employee.setEmail(normalizedEmail);
        employee.setMobileNumber(request.getMobileNumber() != null ? request.getMobileNumber().trim() : null);
        employee.setDesignation(request.getDesignation() != null ? request.getDesignation().trim() : null);
        employee.setJoiningDate(request.getJoiningDate());
        employee.setOrganization(organization);
        employee.setDepartment(department);

        if (request.getActive() != null) {
            employee.setActive(request.getActive());
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Employee ID: {} updated successfully", id);

        return mapToResponse(updatedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        log.debug("Fetching employee by ID: {}", id);
        Employee employee = employeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID : " + id));

        return mapToResponse(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        log.debug("Fetching all non-deleted employees");
        return employeeRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllActiveEmployees() {
        log.debug("Fetching all active non-deleted employees");
        return employeeRepository.findByActiveTrueAndDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployeesByOrganization(Long organizationId) {
        log.debug("Fetching employees for organization ID: {}", organizationId);
        return employeeRepository.findByOrganizationIdAndDeletedFalse(organizationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getEmployeesByDepartment(Long departmentId) {
        log.debug("Fetching employees for department ID: {}", departmentId);
        return employeeRepository.findByDepartmentIdAndDeletedFalse(departmentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void activateEmployee(Long id) {
        log.info("Activating employee ID: {}", id);
        Employee employee = employeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID : " + id));

        employee.setActive(true);
        employeeRepository.save(employee);
    }

    @Override
    public void deactivateEmployee(Long id) {
        log.info("Deactivating employee ID: {}", id);
        Employee employee = employeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID : " + id));

        employee.setActive(false);
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Soft-deleting employee ID: {}", id);
        Employee employee = employeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID : " + id));

        employee.setDeleted(true);
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    /**
     * Convert Employee Entity to EmployeeResponse DTO
     */
    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .mobileNumber(employee.getMobileNumber())
                .designation(employee.getDesignation())
                .joiningDate(employee.getJoiningDate())
                .organizationId(employee.getOrganization().getId())
                .organizationName(employee.getOrganization().getOrganizationName())
                .departmentId(employee.getDepartment().getId())
                .departmentName(employee.getDepartment().getDepartmentName())
                .active(employee.getActive())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}