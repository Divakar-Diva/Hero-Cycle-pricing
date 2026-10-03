package com.divakar.heroCycle.configuration.service;

import com.divakar.heroCycle.component.entity.Component;
import com.divakar.heroCycle.component.service.ComponentService;
import com.divakar.heroCycle.configuration.dto.ConfigurationItemDto;
import com.divakar.heroCycle.configuration.dto.ConfigurationItemResponseDto;
import com.divakar.heroCycle.configuration.dto.ConfigurationResponseDto;
import com.divakar.heroCycle.configuration.dto.CreateConfigurationDto;
import com.divakar.heroCycle.configuration.entity.ConfigurationItem;
import com.divakar.heroCycle.configuration.entity.CycleConfiguration;
import com.divakar.heroCycle.configuration.repository.CycleConfigurationRepository;
import com.divakar.heroCycle.exception.ConfigurationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CycleConfigurationService {

    private final CycleConfigurationRepository configurationRepository;
    private final ComponentService componentService; // injected via DI

    // Create a new cycle configuration with multiple components
    @Transactional
    public ConfigurationResponseDto createConfiguration(CreateConfigurationDto request) {

        // Build the configuration
        CycleConfiguration configuration = CycleConfiguration.builder()
                .name(request.getName())
                .description(request.getDescription())
                .active(true)
                .build();

        // For each component in the request, fetch it and add it as an item
        for (ConfigurationItemDto itemDto : request.getItems()) {
            Component component = componentService.findActiveById(itemDto.getComponentId());

            ConfigurationItem item = ConfigurationItem.builder()
                    .component(component)
                    .quantity(itemDto.getQuantity())
                    .build();

            configuration.addItem(item);
        }

        CycleConfiguration saved = configurationRepository.save(configuration);
        return mapToResponse(saved);
    }

    // Get all active configurations
    @Transactional(readOnly = true)
    public List<ConfigurationResponseDto> getAllConfigurations() {
        List<CycleConfiguration> configurations = configurationRepository.findAllByActiveTrue();
        return configurations.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get one configuration by id
    @Transactional(readOnly = true)
    public ConfigurationResponseDto getConfigurationById(Long id) {
        CycleConfiguration configuration = findById(id);
        return mapToResponse(configuration);
    }

    // Deactivate (soft delete) a configuration
    @Transactional
    public void deactivateConfiguration(Long id) {
        CycleConfiguration configuration = findById(id);
        configuration.setActive(false);
    }

    // Helper: find configuration or throw exception
    private CycleConfiguration findById(Long id) {
        return configurationRepository.findById(id)
                .orElseThrow(() -> new ConfigurationNotFoundException(
                        "Configuration not found with id: " + id
                ));
    }

    // Helper: convert entity to response DTO and calculate total price
    private ConfigurationResponseDto mapToResponse(CycleConfiguration configuration) {
        List<ConfigurationItemResponseDto> itemResponses = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (ConfigurationItem item : configuration.getItems()) {
            Component component = item.getComponent();

            // subtotal = component price * quantity
            BigDecimal subtotal = component.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            ConfigurationItemResponseDto itemResponse = new ConfigurationItemResponseDto();
            itemResponse.setItemId(item.getId());
            itemResponse.setComponentId(component.getId());
            itemResponse.setComponentName(component.getName());
            itemResponse.setComponentType(component.getType());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setUnitPrice(component.getPrice());
            itemResponse.setSubtotal(subtotal);

            itemResponses.add(itemResponse);
            totalPrice = totalPrice.add(subtotal);
        }

        ConfigurationResponseDto response = new ConfigurationResponseDto();
        response.setId(configuration.getId());
        response.setName(configuration.getName());
        response.setDescription(configuration.getDescription());
        response.setActive(configuration.isActive());
        response.setItems(itemResponses);
        response.setTotalPrice(totalPrice);

        return response;
    }
}
