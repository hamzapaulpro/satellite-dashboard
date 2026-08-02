package com.satellitedashboard.backend.service;

import com.satellitedashboard.backend.model.Satellite;
import org.springframework.stereotype.Service;

@Service
public class TleParserService {

    private static final double EARTH_RADIUS_KM = 6378.137;
    private static final double GM = 398600.4418; // km^3/s^2, Earth's gravitational parameter

    public Satellite parse(String[] tleEntry) {
        String name = tleEntry[0];
        String line1 = tleEntry[1];
        String line2 = tleEntry[2];

        String noradId = line1.substring(2, 7).strip();

        double inclinationDeg = Double.parseDouble(line2.substring(8, 16).strip());
        double meanMotionRevPerDay = Double.parseDouble(line2.substring(52, 63).strip());

        // Convert mean motion to rad/s
        double meanMotionRadPerSec = meanMotionRevPerDay * 2 * Math.PI / 86400.0;

        // Kepler's third law: semi-major axis from mean motion
        double semiMajorAxisKm = Math.cbrt(GM / (meanMotionRadPerSec * meanMotionRadPerSec));

        double altitudeKm = semiMajorAxisKm - EARTH_RADIUS_KM;

        // Circular-orbit approximation of orbital velocity (vis-viva equation)
        double velocityKmS = Math.sqrt(GM / semiMajorAxisKm);

        return new Satellite(noradId, name, round(altitudeKm), round(inclinationDeg), round(velocityKmS));
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
