package com.example.bigstest.inquireapplication.controller;

import com.example.bigstest.domain.entity.WeatherForecast;
import com.example.bigstest.domain.service.WeatherForecastService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherInquireController {

    private final WeatherForecastService weatherForecastService;

    @GetMapping("/forecasts")
    public ResponseEntity<List<WeatherForecast>> getWeatherForecasts() {
        List<WeatherForecast> forecasts = weatherForecastService.findAllWeatherForecasts();
        if (forecasts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(forecasts);
    }
}