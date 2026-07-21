package com.company.taskportal.repository;

import com.company.taskportal.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByIdAndDeletedFalse(Long id);

    Optional<Project> findByProjectCodeAndDeletedFalse(String projectCode);

    Optional<Project> findByProjectNameAndDeletedFalse(String projectName);

    boolean existsByProjectCodeAndDeletedFalse(String projectCode);

    boolean existsByProjectNameAndDeletedFalse(String projectName);

    boolean existsByProjectCodeAndIdNotAndDeletedFalse(String projectCode, Long id);

    boolean existsByProjectNameAndIdNotAndDeletedFalse(String projectName, Long id);

    List<Project> findByDeletedFalse();

    List<Project> findByActiveTrueAndDeletedFalse();

    List<Project> findByActiveFalseAndDeletedFalse();

    List<Project> findByOrganizationIdAndDeletedFalse(Long organizationId);

}