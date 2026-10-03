package com.divakar.heroCycle.configuration.controller;

import com.divakar.heroCycle.configuration.dto.ConfigurationResponseDto;
import com.divakar.heroCycle.configuration.dto.CreateConfigurationDto;
import com.divakar.heroCycle.configuration.service.CycleConfigurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
@RequiredArgsConstructor
public class CycleConfigurationController {

    private final CycleConfigurationService configurationService;


    @PostMapping
    public ResponseEntity<ConfigurationResponseDto> createConfiguration(
            @Valid @RequestBody CreateConfigurationDto request) {
        ConfigurationResponseDto response = configurationService.createConfiguration(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ConfigurationResponseDto>> getAllConfigurations() {
        return ResponseEntity.ok(configurationService.getAllConfigurations());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationResponseDto> getConfigurationById(@PathVariable Long id) {
        return ResponseEntity.ok(configurationService.getConfigurationById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateConfiguration(@PathVariable Long id) {
        configurationService.deactivateConfiguration(id);
        return ResponseEntity.noContent().build();
    }
}
