package com.satellitedashboard.backend.config;

import jakarta.annotation.PostConstruct;
import org.orekit.data.DataContext;
import org.orekit.data.DirectoryCrawler;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URL;

@Configuration
public class OrekitConfig {

    @PostConstruct
    public void init() throws Exception {
        URL url = getClass().getClassLoader().getResource("orekit-data");
        File orekitData = new File(url.toURI());
        DataContext.getDefault().getDataProvidersManager()
                .addProvider(new DirectoryCrawler(orekitData));
    }
}
