package com.divakar.heroCycle.configuration.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateConfigurationDto {

    @NotBlank(message = "Configuration name is required")
    private String name;

    private String description;

    @NotEmpty(message = "At least one component is required")
    @Valid
    private List<ConfigurationItemDto> items;
}
