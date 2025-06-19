package com.app.weather.service;

import com.app.weather.integration.WeatherApiClient;
import com.app.weather.models.WeatherData;
import com.app.weather.models.WeatherRequest;
import com.app.weather.models.WeatherSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceImplTest {
    private WeatherApiClient weatherApiClient;
    private WeatherMapper weatherMapper;
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        weatherApiClient = mock(WeatherApiClient.class);
        weatherMapper = mock(WeatherMapper.class);
        weatherService = new WeatherServiceImpl(weatherApiClient, weatherMapper);
    }

    @Test
    void getWeatherData_returnsWeatherSummary() {
        WeatherRequest request = new WeatherRequest();
        WeatherData data = new WeatherData();
        WeatherSummary summary = new WeatherSummary();

        when(weatherApiClient.fetchWeatherData(request)).thenReturn(data);
        when(weatherMapper.toWeatherSummary(data, request.forecastDays, request.pastDays)).thenReturn(summary);

        WeatherSummary result = weatherService.getWeatherData(request);
        assertNotNull(result);
        assertEquals(summary, result);
        verify(weatherApiClient).fetchWeatherData(request);
        verify(weatherMapper).toWeatherSummary(data, request.forecastDays, request.pastDays);
    }
}

