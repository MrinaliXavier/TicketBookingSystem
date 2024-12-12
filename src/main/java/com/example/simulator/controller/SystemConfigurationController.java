package com.example.simulator.controller;

import com.example.simulator.entity.SystemConfiguration;
import com.example.simulator.service.SystemConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configurations")
public class SystemConfigurationController {

    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @PostMapping
    public SystemConfiguration saveConfig(@RequestBody SystemConfiguration config) {
        return systemConfigurationService.saveConfiguration(config);
    }

    @GetMapping("/{id}")
    public SystemConfiguration getConfig(@PathVariable Long id) {
        return systemConfigurationService.getConfiguration(id);
    }

    @PostMapping("/{id}/start")
    public SystemConfiguration startSystem(@PathVariable Long id) {
        return systemConfigurationService.startSystem(id);
    }

    @PostMapping("/{id}/stop")
    public SystemConfiguration stopSystem(@PathVariable Long id) {
        return systemConfigurationService.stopSystem(id);
    }
}
