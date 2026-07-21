package com.company.taskportal.service;

import com.company.taskportal.dto.DepartmentRequest;
import com.company.taskportal.dto.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    void deleteDepartment(Long id);

    void activateDepartment(Long id);

    void deactivateDepartment(Long id);
}