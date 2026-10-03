package com.divakar.heroCycle.configuration.repository;

import com.divakar.heroCycle.configuration.entity.CycleConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleConfigurationRepository extends JpaRepository<CycleConfiguration, Long> {

    // get all active configurations
    List<CycleConfiguration> findAllByActiveTrue();
}
