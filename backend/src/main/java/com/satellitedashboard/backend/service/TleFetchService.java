package com.satellitedashboard.backend.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class TleFetchService {

    private static final String CELESTRAK_URL = "https://celestrak.org/NORAD/elements/gp.php?GROUP=stations&FORMAT=tle";
    private final RestClient restClient = RestClient.create();

    @Cacheable("tleData")
    public List<String[]> fetchRawTleEntries() {
        System.out.println("Fetching fresh TLE data from Celestrak...");

        String rawText = restClient.get()
                .uri(CELESTRAK_URL)
                .retrieve()
                .body(String.class);

        List<String[]> entries = new ArrayList<>();
        String[] lines = rawText.strip().split("\\r?\\n");

        for (int i = 0; i + 2 < lines.length; i += 3) {
            entries.add(new String[] {
                    lines[i].strip(),
                    lines[i + 1].strip(),
                    lines[i + 2].strip()
            });
        }

        return entries;
    }

    // Runs every 3 hours, clears the cache so the next call re-fetches fresh data
    @Scheduled(fixedRate = 3 * 60 * 60 * 1000)
    @CacheEvict(value = "tleData", allEntries = true)
    public void evictCache() {
        System.out.println("Evicting TLE cache — next request will fetch fresh data");
    }

}
