package com.satellitedashboard.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Satellite {
    private String noradId;
    private String name;
    private double altitudeKm;
    private double inclinationDeg;
    private double velocityKmS;
}
