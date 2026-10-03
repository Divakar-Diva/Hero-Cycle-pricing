package com.divakar.heroCycle.component.repository;

import com.divakar.heroCycle.component.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository extends JpaRepository<Component, Long> {

    List<Component> findAllByActiveTrueOrderByNameAsc();

    Optional<Component> findByIdAndActiveTrue(Long id);
}
