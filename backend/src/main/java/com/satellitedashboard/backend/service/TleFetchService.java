package com.satellitedashboard.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class TleFetchService {

    private static final String CELESTRAK_URL = "https://celestrak.org/NORAD/elements/gp.php?GROUP=stations&FORMAT=tle";
    private final RestClient restClient = RestClient.create();

    public List<String[]> fetchRawTleEntries() {
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

}
