package com.example.bigstest.syncapplication.service;

import com.example.bigstest.domain.entity.WeatherForecast;
import com.example.bigstest.domain.repository.WeatherForecastRepository;
import com.example.bigstest.internal.service.WeatherApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherSyncService {

    private final WeatherApiService weatherApiService;
    private final WeatherForecastRepository weatherForecastRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void syncWeatherData() {
        String baseDate = "20240430";
        String baseTime = "0500";
        String jsonResponse = weatherApiService.fetchWeatherData(baseDate, baseTime, "62", "130");

        try {
            WeatherForecast forecast = parseJsonToWeatherForecast(jsonResponse);
            weatherForecastRepository.save(forecast);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WeatherForecast parseJsonToWeatherForecast(String json) throws IOException {
        JsonNode root = objectMapper.readTree(json);
        return WeatherForecast.builder()
            .baseDate(root.path("baseDate").asText())
            .baseTime(root.path("baseTime").asText())
            .category(root.path("category").asText())
            .fcstValue(root.path("fcstValue").asText())
            .nx(root.path("nx").asText())
            .ny(root.path("ny").asText())
            .build();
    }
}