package com.example.bigstest.syncapplication.service;

import com.example.bigstest.domain.dto.WeatherForecastResponseDto;
import com.example.bigstest.domain.entity.WeatherForecast;
import com.example.bigstest.domain.repository.WeatherForecastRepository;
import com.example.bigstest.internal.service.WeatherApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    public void syncWeatherData(String baseDate, String baseTime) {
        String jsonResponse = weatherApiService.fetchWeatherData(baseDate, baseTime, "62", "130");

        try {
            List<WeatherForecastResponseDto> forecastDtos = parseJsonToWeatherForecastResponseDtos(jsonResponse);
            if (!forecastDtos.isEmpty()) {
                List<WeatherForecast> forecasts = forecastDtos.stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
                weatherForecastRepository.saveAll(forecasts);
                System.out.println("데이터베이스에 " + forecasts.size() + "개의 일기예보를 저장했습니다.");
            } else {
                System.out.println("저장할 날씨정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("날씨 데이터 동기화 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("날씨 데이터 동기화 실패", e);
        }
    }

    private List<WeatherForecastResponseDto> parseJsonToWeatherForecastResponseDtos(String json) throws IOException {
        List<WeatherForecastResponseDto> forecastDtos = new ArrayList<>();
        JsonNode items = objectMapper.readTree(json)
            .path("response").path("body").path("items").path("item");
        if (items.isArray()) {
            for (JsonNode item : items) {
                WeatherForecastResponseDto dto = new WeatherForecastResponseDto(
                    item.path("baseDate").asText(),
                    item.path("baseTime").asText(),
                    item.path("category").asText(),
                    item.path("fcstValue").asText()
                );
                forecastDtos.add(dto);
            }
        }
        return forecastDtos;
    }

    private WeatherForecast convertToEntity(WeatherForecastResponseDto dto) {
        return WeatherForecast.builder()
            .baseDate(dto.baseDate())
            .baseTime(dto.baseTime())
            .category(dto.category())
            .fcstValue(dto.fcstValue())
            .nx("62")
            .ny("130")
            .build();
    }
}