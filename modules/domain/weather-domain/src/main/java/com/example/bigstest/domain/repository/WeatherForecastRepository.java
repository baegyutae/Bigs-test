package com.example.bigstest.domain.repository;

import com.example.bigstest.domain.entity.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherForecastRepository extends JpaRepository<WeatherForecast, Long> {

}
