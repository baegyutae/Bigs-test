package com.example.bigstest.syncapplication.controller;

import com.example.bigstest.syncapplication.service.WeatherSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherSyncController {

    private final WeatherSyncService weatherSyncService;

    @PostMapping("/sync")
    public ResponseEntity<String> syncWeatherData(@RequestParam String baseDate, @RequestParam String baseTime) {
        try {
            weatherSyncService.syncWeatherData(baseDate, baseTime);
            return ResponseEntity.ok("날씨 데이터가 동기화되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("날씨 데이터 동기화 실패: " + e.getMessage());
        }
    }
}