package com.company.taskportal.repository;

import com.company.taskportal.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    boolean existsByOrganizationCode(String organizationCode);

    boolean existsByEmail(String email);

    List<Organization> findByDeletedFalse();

    Optional<Organization> findByIdAndDeletedFalse(Long id);
}