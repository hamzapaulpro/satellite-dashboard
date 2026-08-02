package com.satellitedashboard.backend.service;

import com.satellitedashboard.backend.model.Satellite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TleParserServiceTest {

    private TleParserService parser;

    // A real, known TLE for the ISS — good as a stable fixture
    private static final String[] ISS_TLE = {
            "ISS (ZARYA)",
            "1 25544U 98067A   24045.51782528  .00016717  00000+0  10270-3 0  9994",
            "2 25544  51.6423  92.0022 0004423  61.3315  46.8100 15.50038117437799"
    };

    @BeforeEach
    void setUp() {
        parser = new TleParserService();
    }

    @Test
    void parsesNoradIdCorrectly() {
        Satellite satellite = parser.parse(ISS_TLE);
        assertEquals("25544", satellite.getNoradId());
    }

    @Test
    void parsesNameCorrectly() {
        Satellite satellite = parser.parse(ISS_TLE);
        assertEquals("ISS (ZARYA)", satellite.getName());
    }

    @Test
    void parsesInclinationWithinExpectedRange() {
        Satellite satellite = parser.parse(ISS_TLE);
        // ISS inclination is well-known to be ~51.6 degrees
        assertEquals(51.6423, satellite.getInclinationDeg(), 0.001);
    }

    @Test
    void computesAltitudeWithinPlausibleIssRange() {
        Satellite satellite = parser.parse(ISS_TLE);
        // ISS orbits roughly 400-420km altitude
        assertTrue(satellite.getAltitudeKm() > 350 && satellite.getAltitudeKm() < 450,
                "Expected ISS altitude between 350-450km, got: " + satellite.getAltitudeKm());
    }

    @Test
    void computesVelocityWithinPlausibleLeoRange() {
        Satellite satellite = parser.parse(ISS_TLE);
        // LEO orbital velocity is typically 7.5-7.8 km/s
        assertTrue(satellite.getVelocityKmS() > 7.0 && satellite.getVelocityKmS() < 8.0,
                "Expected LEO velocity between 7.0-8.0 km/s, got: " + satellite.getVelocityKmS());
    }
}