package com.divakar.heroCycle.component.controller;

import com.divakar.heroCycle.component.dto.ComponentResponseDto;
import com.divakar.heroCycle.component.dto.CreateComponentDto;
import com.divakar.heroCycle.component.dto.UpdateComponentDto;
import com.divakar.heroCycle.component.service.ComponentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @PostMapping
    public ResponseEntity<ComponentResponseDto> addComponent(@Valid @RequestBody CreateComponentDto request) {
        ComponentResponseDto response = componentService.addComponent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ComponentResponseDto>> getAllComponents() {
        return ResponseEntity.ok(componentService.getAllComponents());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponseDto> getComponentById(@PathVariable Long id) {
        return ResponseEntity.ok(componentService.getComponentById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponseDto> updateComponent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateComponentDto request) {
        return ResponseEntity.ok(componentService.updateComponent(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateComponent(@PathVariable Long id) {
        componentService.deactivateComponent(id);
        return ResponseEntity.noContent().build();
    }
}
