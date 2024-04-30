package com.example.bigstest.internal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class WeatherApiService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.baseUrl}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    public String fetchWeatherData(String baseDate, String baseTime, String nx, String ny) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .queryParam("serviceKey", apiKey)
            .queryParam("numOfRows", "10")
            .queryParam("pageNo", "1")
            .queryParam("dataType", "JSON")
            .queryParam("base_date", baseDate)
            .queryParam("base_time", baseTime)
            .queryParam("nx", nx)
            .queryParam("ny", ny)
            .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}