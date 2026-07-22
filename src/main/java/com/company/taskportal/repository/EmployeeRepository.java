package com.company.taskportal.repository;

import com.company.taskportal.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // =========================
    // Find Employee
    // =========================

    Optional<Employee> findByIdAndDeletedFalse(Long id);

    Optional<Employee> findByEmployeeCodeAndDeletedFalse(String employeeCode);

    Optional<Employee> findByEmailAndDeletedFalse(String email);

    // =========================
    // Exists Validation
    // =========================

    boolean existsByEmployeeCodeAndDeletedFalse(String employeeCode);

    boolean existsByEmailAndDeletedFalse(String email);

    boolean existsByEmployeeCodeAndIdNotAndDeletedFalse(String employeeCode, Long id);

    boolean existsByEmailAndIdNotAndDeletedFalse(String email, Long id);

    // =========================
    // Employee Lists
    // =========================

    List<Employee> findByDeletedFalse();

    List<Employee> findByActiveTrueAndDeletedFalse();

    List<Employee> findByActiveFalseAndDeletedFalse();

    List<Employee> findByOrganizationIdAndDeletedFalse(Long organizationId);

    List<Employee> findByDepartmentIdAndDeletedFalse(Long departmentId);

    long countByDeletedFalse();

    long countByActiveTrueAndDeletedFalse();
}