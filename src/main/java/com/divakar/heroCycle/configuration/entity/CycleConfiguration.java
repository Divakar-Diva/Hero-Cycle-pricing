package com.divakar.heroCycle.configuration.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cycle_configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CycleConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private boolean active = true;

    @OneToMany(
            mappedBy = "configuration",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<ConfigurationItem> items = new ArrayList<>();

    public void addItem(ConfigurationItem item) {
        items.add(item);
        item.setConfiguration(this);
    }
}
