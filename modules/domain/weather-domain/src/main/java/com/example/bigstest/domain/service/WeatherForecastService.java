package com.example.bigstest.domain.service;

import com.example.bigstest.domain.entity.WeatherForecast;
import com.example.bigstest.domain.repository.WeatherForecastRepository;
import java.util.List;
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
    public List<WeatherForecast> findAllWeatherForecasts() {
        return weatherForecastRepository.findAll();
    }

    @Transactional(readOnly = true)
    public WeatherForecast findWeatherForecastById(Long id) {
        return weatherForecastRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("일기예보를 찾을 수 없습니다."));
    }

}
