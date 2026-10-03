package com.divakar.heroCycle.configuration.entity;

import com.divakar.heroCycle.component.entity.Component;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "configuration_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigurationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuration_id", nullable = false)
    private CycleConfiguration configuration;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @Column(nullable = false)
    private Integer quantity;
}
