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

    /***
     * private이므로 properties에 있는 키를 인식하지 못함
     * 방법을 찾는 중
     */
    @Value("${weather.api.baseUrl}")
    String baseUrl;

    @Value("${weather.api.key}")
    String apiKey;

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