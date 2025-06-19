package com.app.weather.service;

import com.app.weather.integration.WeatherApiClient;
import com.app.weather.models.WeatherRequest;
import com.app.weather.models.WeatherSummary;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final WeatherApiClient weatherApiClient;
    private final WeatherMapper weatherMapper;

    public WeatherServiceImpl(WeatherApiClient weatherApiClient, WeatherMapper weatherMapper) {
        this.weatherApiClient = weatherApiClient;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public WeatherSummary getWeatherData(WeatherRequest request) {
        var data = weatherApiClient.fetchWeatherData(request);
        return weatherMapper.toWeatherSummary(data, request.forecastDays, request.pastDays);
    }
}

