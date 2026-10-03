package com.divakar.heroCycle.configuration.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConfigurationItemResponseDto {

    private Long itemId;
    private Long componentId;
    private String componentName;
    private String componentType;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal; // unitPrice * quantity
}
