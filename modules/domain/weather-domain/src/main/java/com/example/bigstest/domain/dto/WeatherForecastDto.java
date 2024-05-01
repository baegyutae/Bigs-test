package com.example.bigstest.domain.dto;

public record WeatherForecastDto(
    Long id,
    String baseDate,
    String baseTime,
    String category,
    String fcstValue,
    String nx,
    String ny
) {

}
