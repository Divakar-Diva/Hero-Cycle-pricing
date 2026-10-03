package com.divakar.heroCycle.component.service;

import com.divakar.heroCycle.component.dto.ComponentResponseDto;
import com.divakar.heroCycle.component.dto.CreateComponentDto;
import com.divakar.heroCycle.component.dto.UpdateComponentDto;
import com.divakar.heroCycle.component.entity.Component;
import com.divakar.heroCycle.component.repository.ComponentRepository;
import com.divakar.heroCycle.exception.ComponentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentService {

    private final ComponentRepository componentRepository;

    @Transactional
    public ComponentResponseDto addComponent(CreateComponentDto request) {

        Component component = Component.builder()
                .name(request.getName())
                .type(request.getType().toUpperCase())
                .description(request.getDescription())
                .quality(request.getQuality())
                .price(request.getPrice())
                .active(true)
                .build();

        Component saved = componentRepository.save(component);
        return mapToResponse(saved);
    }


    @Transactional(readOnly = true)
    public List<ComponentResponseDto> getAllComponents() {
        List<Component> components = componentRepository.findAllByActiveTrueOrderByNameAsc();
        return components.stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public ComponentResponseDto getComponentById(Long id) {
        Component component = findActiveById(id);
        return mapToResponse(component);
    }


    @Transactional
    public ComponentResponseDto updateComponent(Long id, UpdateComponentDto request) {
        Component component = findActiveById(id);

        component.setName(request.getName());
        component.setType(request.getType().toUpperCase());
        component.setDescription(request.getDescription());
        component.setQuality(request.getQuality());
        component.setPrice(request.getPrice());

        return mapToResponse(component);
    }


    @Transactional
    public void deactivateComponent(Long id) {
        Component component = findActiveById(id);
        component.setActive(false);
    }


    public Component findActiveById(Long id) {
        return componentRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ComponentNotFoundException(
                        "Component not found with id: " + id
                ));
    }

    private ComponentResponseDto mapToResponse(Component component) {
        ComponentResponseDto response = new ComponentResponseDto();
        response.setId(component.getId());
        response.setName(component.getName());
        response.setType(component.getType());
        response.setDescription(component.getDescription());
        response.setQuality(component.getQuality());
        response.setPrice(component.getPrice());
        response.setActive(component.isActive());
        return response;
    }
}
