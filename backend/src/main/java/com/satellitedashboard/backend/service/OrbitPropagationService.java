package com.satellitedashboard.backend.service;

import com.satellitedashboard.backend.model.OrbitPoint;
import org.hipparchus.geometry.euclidean.threed.Vector3D;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrbitPropagationService {

    private static final double EARTH_RADIUS_KM = 6378.137;
    private final TleFetchService tleFetchService;

    public OrbitPropagationService(TleFetchService tleFetchService) {
        this.tleFetchService = tleFetchService;
    }

    public List<OrbitPoint> propagate(String noradId, int hours, int stepMinutes) {
        String[] entry = tleFetchService.findByNoradId(noradId);
        TLE tle = new TLE(entry[1], entry[2]);
        TLEPropagator propagator = TLEPropagator.selectExtrapolator(tle);

        List<OrbitPoint> points = new ArrayList<>();
        AbsoluteDate start = tle.getDate();
        long totalSteps = (long) hours * 60 / stepMinutes;

        for (long i = 0; i <= totalSteps; i++) {
            AbsoluteDate date = start.shiftedBy(i * stepMinutes * 60.0);
            SpacecraftState state = propagator.propagate(date);

            Vector3D position = state.getPVCoordinates().getPosition();
            Vector3D velocity = state.getPVCoordinates().getVelocity();

            double altitudeKm = position.getNorm() / 1000.0 - EARTH_RADIUS_KM;
            double velocityKmS = velocity.getNorm() / 1000.0;

            points.add(new OrbitPoint(date.toString(), round(altitudeKm), round(velocityKmS)));
        }
        return points;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
