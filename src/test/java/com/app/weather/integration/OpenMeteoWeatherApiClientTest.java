package com.app.weather.integration;

import com.app.weather.models.WeatherData;
import com.app.weather.models.WeatherRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OpenMeteoWeatherApiClientTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenMeteoWeatherApiClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchWeatherData_returnsWeatherData() {
        WeatherRequest request = new WeatherRequest();
        WeatherData expected = new WeatherData();
        when(restTemplate.getForObject(anyString(), eq(WeatherData.class))).thenReturn(expected);
        WeatherData result = client.fetchWeatherData(request);
        assertNotNull(result);
        verify(restTemplate).getForObject(anyString(), eq(WeatherData.class));
    }
}
