package com.example.bigstest.domain.service;

import com.example.bigstest.domain.dto.WeatherForecastResponseDto;
import com.example.bigstest.domain.entity.WeatherForecast;
import com.example.bigstest.domain.repository.WeatherForecastRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WeatherForecastService {

    private final WeatherForecastRepository weatherForecastRepository;

    @Transactional
    public WeatherForecast saveWeatherForecast(WeatherForecast weatherForecast) {
        return weatherForecastRepository.save(weatherForecast);
    }

    @Transactional(readOnly = true)
    public List<WeatherForecastResponseDto> findAllWeatherForecastsDto() {
        return weatherForecastRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private WeatherForecastResponseDto convertToDto(WeatherForecast weatherForecast) {
        return new WeatherForecastResponseDto(
            weatherForecast.getBaseDate(),
            weatherForecast.getBaseTime(),
            weatherForecast.getCategory(),
            weatherForecast.getFcstValue()
        );
    }

    @Transactional(readOnly = true)
    public WeatherForecast findWeatherForecastById(Long id) {
        return weatherForecastRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("일기예보를 찾을 수 없습니다."));
    }

}
