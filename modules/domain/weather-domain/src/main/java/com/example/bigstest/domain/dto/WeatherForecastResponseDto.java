package com.example.bigstest.domain.dto;

public record WeatherForecastResponseDto(
    String baseDate,
    String baseTime,
    String category,
    String fcstValue
) {

}
