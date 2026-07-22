package com.company.taskportal.repository;

import com.company.taskportal.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByIdAndDeletedFalse(Long id);

    List<Department> findByDeletedFalse();

    boolean existsByDepartmentCode(String departmentCode);

    boolean existsByOrganization_IdAndDepartmentName(
            Long organizationId,
            String departmentName
    );

    // Fixed: changed return type from boolean to long
    long countByDeletedFalse();
}