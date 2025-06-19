package com.app.weather.integration;

import com.app.weather.models.WeatherData;
import com.app.weather.models.WeatherRequest;

public interface WeatherApiClient {
    WeatherData fetchWeatherData(WeatherRequest request);
}

