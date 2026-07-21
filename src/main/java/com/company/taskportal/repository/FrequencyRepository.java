package com.company.taskportal.repository;

import com.company.taskportal.entity.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FrequencyRepository extends JpaRepository<Frequency, Long> {

    Optional<Frequency> findByIdAndDeletedFalse(Long id);

    List<Frequency> findByDeletedFalse();

    List<Frequency> findByActiveTrueAndDeletedFalse();

    Optional<Frequency> findByFrequencyCode(String frequencyCode);

    Optional<Frequency> findByFrequencyName(String frequencyName);

    Optional<Frequency> findByFrequencyCodeAndDeletedFalse(String frequencyCode);

    Optional<Frequency> findByFrequencyNameAndDeletedFalse(String frequencyName);

    boolean existsByFrequencyCode(String frequencyCode);

    boolean existsByFrequencyName(String frequencyName);

    boolean existsByFrequencyCodeAndDeletedFalse(String frequencyCode);

    boolean existsByFrequencyNameAndDeletedFalse(String frequencyName);
}