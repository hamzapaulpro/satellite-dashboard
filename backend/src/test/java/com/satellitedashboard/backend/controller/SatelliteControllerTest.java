package com.satellitedashboard.backend.controller;

import com.satellitedashboard.backend.model.Satellite;
import com.satellitedashboard.backend.service.OrbitPropagationService;
import com.satellitedashboard.backend.service.SatelliteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.cache.autoconfigure.CacheAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SatelliteController.class)
@ImportAutoConfiguration(CacheAutoConfiguration.class)
class SatelliteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SatelliteService satelliteService;

    @MockitoBean
    private OrbitPropagationService orbitPropagationService;

    @Test
    void getAllSatellites_returnsOkAndJsonList() throws Exception {
        when(satelliteService.getAllSatellites()).thenReturn(List.of(
                new Satellite("25544", "ISS (ZARYA)", 408.0, 51.6, 7.66)
        ));

        mockMvc.perform(get("/api/satellites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noradId").value("25544"))
                .andExpect(jsonPath("$[0].name").value("ISS (ZARYA)"));
    }
}