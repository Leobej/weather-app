package com.app.weather.service;

import com.app.weather.models.WeatherRequest;
import com.app.weather.models.WeatherSummary;

public interface WeatherService {
    WeatherSummary getWeatherData(WeatherRequest request);
}

