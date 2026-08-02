package com.satellitedashboard.backend.service;

import com.satellitedashboard.backend.model.Satellite;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SatelliteService {

    public List<Satellite> getAllSatellites() {
        return List.of(
            new Satellite("25544", "ISS (ZARYA)", 408.0, 51.6, 7.66),
            new Satellite("48274", "STARLINK-2000", 550.0, 53.0, 7.59),
            new Satellite("43013", "SENTINEL-2B", 786.0, 98.6, 7.46)
        );
    }
}
