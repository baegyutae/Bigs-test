package com.example.bigstest.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "weather_forecast")
@Builder
public class WeatherForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String baseDate; // 발표일자

    @Column(nullable = false)
    private String baseTime; // 발표시각

    @Column(nullable = false)
    private String category; // 자료구분코드

    @Column(nullable = false)
    private String fcstValue; // 예보 값

    @Column(nullable = false)
    private String nx; // X 좌표

    @Column(nullable = false)
    private String ny; // Y 좌표

}
