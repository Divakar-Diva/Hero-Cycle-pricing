package com.divakar.heroCycle.component.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateComponentDto {

    @NotBlank(message = "Component name is required")
    private String name;

    @NotBlank(message = "Component type is required Example (Tyre, Gear..... )")
    private String type;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Quality is required Example (GOOD, PREMIUM, STANDARD )")
    private String quality;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal price;
}
