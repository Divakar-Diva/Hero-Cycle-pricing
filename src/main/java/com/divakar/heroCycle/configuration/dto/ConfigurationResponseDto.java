package com.divakar.heroCycle.configuration.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ConfigurationResponseDto {

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private List<ConfigurationItemResponseDto> items;
    private BigDecimal totalPrice; // sum of all item subtotals
}
