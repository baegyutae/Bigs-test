package com.example.bigstest.syncapplication.service;

import com.example.bigstest.domain.entity.WeatherForecast;
import com.example.bigstest.domain.repository.WeatherForecastRepository;
import com.example.bigstest.internal.service.WeatherApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WeatherSyncService {

    private final WeatherApiService weatherApiService;
    private final WeatherForecastRepository weatherForecastRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public void syncWeatherData() {
        String baseDate = "20240430";
        String baseTime = "0500";
        String jsonResponse = weatherApiService.fetchWeatherData(baseDate, baseTime, "62", "130");

        try {
            List<WeatherForecast> forecasts = parseJsonToWeatherForecasts(jsonResponse);
            if (!forecasts.isEmpty()) {
                weatherForecastRepository.saveAll(forecasts);
                System.out.println("데이터베이스에 " + forecasts.size() + "개의 일기예보를 저장했습니다.");
            } else {
                System.out.println("저장할 날씨정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<WeatherForecast> parseJsonToWeatherForecasts(String json) throws IOException {
        List<WeatherForecast> forecasts = new ArrayList<>();
        try {
            JsonNode items = objectMapper.readTree(json)
                .path("response").path("body").path("items").path("item");
            if (items.isArray()) {
                for (JsonNode item : items) {
                    WeatherForecast forecast = WeatherForecast.builder()
                        .baseDate(item.path("baseDate").asText())
                        .baseTime(item.path("baseTime").asText())
                        .category(item.path("category").asText())
                        .fcstValue(item.path("fcstValue").asText())
                        .nx(item.path("nx").asText())
                        .ny(item.path("ny").asText())
                        .build();
                    forecasts.add(forecast);
                }
            }
        } catch (IOException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            throw e;
        }
        return forecasts;
    }
}