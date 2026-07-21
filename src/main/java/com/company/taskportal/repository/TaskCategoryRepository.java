package com.company.taskportal.repository;

import com.company.taskportal.entity.Organization;
import com.company.taskportal.entity.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {

    Optional<TaskCategory> findByIdAndDeletedFalse(Long id);

    List<TaskCategory> findByDeletedFalse();

    List<TaskCategory> findByActiveTrueAndDeletedFalse();

    Optional<TaskCategory> findByCategoryCode(String categoryCode);

    Optional<TaskCategory> findByCategoryName(String categoryName);

    Optional<TaskCategory> findByCategoryCodeAndDeletedFalse(String categoryCode);

    Optional<TaskCategory> findByCategoryNameAndDeletedFalse(String categoryName);

    boolean existsByCategoryCode(String categoryCode);

    boolean existsByCategoryName(String categoryName);

    boolean existsByCategoryCodeAndDeletedFalse(String categoryCode);

    boolean existsByCategoryNameAndDeletedFalse(String categoryName);

    List<TaskCategory> findByOrganizationAndDeletedFalse(Organization organization);

}