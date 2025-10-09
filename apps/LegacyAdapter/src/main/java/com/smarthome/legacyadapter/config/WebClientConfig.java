package com.smarthome.legacyadapter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${temperature.service.base-url:http://localhost:8080}")
    private String temperatureServiceBaseUrl;

    @Bean
    public WebClient temperatureWebClient() {
        return WebClient.builder()
                .baseUrl(temperatureServiceBaseUrl)
                .build();
    }
}