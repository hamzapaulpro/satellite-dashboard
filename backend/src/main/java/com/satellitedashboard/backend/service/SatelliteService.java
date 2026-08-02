package com.satellitedashboard.backend.service;

import com.satellitedashboard.backend.model.Satellite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SatelliteService {

    private final TleFetchService tleFetchService;
    private final TleParserService tleParserService;

    public SatelliteService(TleFetchService tleFetchService, TleParserService tleParserService) {
        this.tleFetchService = tleFetchService;
        this.tleParserService = tleParserService;
    }

    public List<Satellite> getAllSatellites() {
        return tleFetchService.fetchRawTleEntries()
                .stream()
                .map(tleParserService::parse)
                .collect(Collectors.toList());
    }
}
