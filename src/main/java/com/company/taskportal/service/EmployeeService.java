package com.company.taskportal.service;

import com.company.taskportal.dto.EmployeeRequest;
import com.company.taskportal.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    /**
     * Create a new employee.
     *
     * @param request Employee request data
     * @return Created employee
     */
    EmployeeResponse createEmployee(EmployeeRequest request);

    /**
     * Update an existing employee.
     *
     * @param id Employee ID
     * @param request Updated employee data
     * @return Updated employee
     */
    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    /**
     * Get employee by ID.
     *
     * @param id Employee ID
     * @return Employee details
     */
    EmployeeResponse getEmployeeById(Long id);

    /**
     * Get all employees.
     *
     * @return List of employees
     */
    List<EmployeeResponse> getAllEmployees();

    /**
     * Get all active employees.
     *
     * @return List of active employees
     */
    List<EmployeeResponse> getAllActiveEmployees();

    /**
     * Get employees by organization.
     *
     * @param organizationId Organization ID
     * @return Employee list
     */
    List<EmployeeResponse> getEmployeesByOrganization(Long organizationId);

    /**
     * Get employees by department.
     *
     * @param departmentId Department ID
     * @return Employee list
     */
    List<EmployeeResponse> getEmployeesByDepartment(Long departmentId);

    /**
     * Activate employee.
     *
     * @param id Employee ID
     */
    void activateEmployee(Long id);

    /**
     * Deactivate employee.
     *
     * @param id Employee ID
     */
    void deactivateEmployee(Long id);

    /**
     * Soft delete employee.
     *
     * @param id Employee ID
     */
    void deleteEmployee(Long id);

}