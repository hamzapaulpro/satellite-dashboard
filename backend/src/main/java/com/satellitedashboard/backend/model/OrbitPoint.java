package com.satellitedashboard.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrbitPoint {
    private String timestamp;
    private double altitudeKm;
    private double velocityKmS;
}
