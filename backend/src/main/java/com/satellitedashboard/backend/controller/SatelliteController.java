package com.satellitedashboard.backend.controller;

import com.satellitedashboard.backend.model.Satellite;
import com.satellitedashboard.backend.service.SatelliteService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/satellites")
@CrossOrigin(origins = "http://localhost:4200")
public class SatelliteController {

    private final SatelliteService satelliteService;

    public SatelliteController(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    @GetMapping
    public List<Satellite> getAllSatellites() {
        return satelliteService.getAllSatellites();
    }

}
