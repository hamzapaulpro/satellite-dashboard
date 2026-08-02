package com.satellitedashboard.backend.controller;

import com.satellitedashboard.backend.model.OrbitPoint;
import com.satellitedashboard.backend.model.Satellite;
import com.satellitedashboard.backend.service.OrbitPropagationService;
import com.satellitedashboard.backend.service.SatelliteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/satellites")
@CrossOrigin(origins = "http://localhost:4200")
public class SatelliteController {

    private final SatelliteService satelliteService;
    private final OrbitPropagationService orbitPropagationService;

    public SatelliteController(SatelliteService satelliteService, OrbitPropagationService orbitPropagationService) {
        this.satelliteService = satelliteService;
        this.orbitPropagationService = orbitPropagationService;
    }

    @GetMapping
    public List<Satellite> getAllSatellites() {
        return satelliteService.getAllSatellites();
    }

    @GetMapping("/{noradId}/orbit")
    public List<OrbitPoint> getOrbit(
            @PathVariable String noradId,
            @RequestParam(defaultValue = "24") int hours,
            @RequestParam(defaultValue = "5") int stepMinutes) {
        return orbitPropagationService.propagate(noradId, hours, stepMinutes);
    }

}
