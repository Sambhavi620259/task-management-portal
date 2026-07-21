package com.company.taskportal.repository;

import com.company.taskportal.entity.EmployeeProfile;
import com.company.taskportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {

    // Find by ID
    Optional<EmployeeProfile> findByIdAndDeletedFalse(Long id);

    // Find by User
    Optional<EmployeeProfile> findByUser(User user);

    // Find by Employee Code
    Optional<EmployeeProfile> findByEmployeeCode(String employeeCode);

    // Find by Aadhaar
    Optional<EmployeeProfile> findByAadhaarNumber(String aadhaarNumber);

    // Find by PAN
    Optional<EmployeeProfile> findByPanNumber(String panNumber);

    // Get all profiles
    List<EmployeeProfile> findByDeletedFalse();

    // Get active profiles
    List<EmployeeProfile> findByActiveTrueAndDeletedFalse();

    // Exists checks
    boolean existsByUser(User user);

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByAadhaarNumber(String aadhaarNumber);

    boolean existsByPanNumber(String panNumber);
}