package com.app.weather.controller;

import com.app.weather.models.WeatherData;
import com.app.weather.models.WeatherRequest;
import com.app.weather.models.WeatherSummary;
import com.app.weather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {
    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @PostMapping("/get-weather")
    public WeatherSummary getWeather(@RequestBody WeatherRequest request) {
        if (request == null || request.latitude == 0 || request.longitude == 0) {
            throw new IllegalArgumentException("Invalid request parameters");
        }
        return service.getWeatherData(request);
    }
}
