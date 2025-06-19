package com.app.weather.integration;

import com.app.weather.models.WeatherData;
import com.app.weather.models.WeatherRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenMeteoWeatherApiClient implements WeatherApiClient {
    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private static final String DAILY_PARAMS = "apparent_temperature_max,apparent_temperature_min,temperature_2m_min,temperature_2m_max,weather_code,wind_speed_10m_max,wind_gusts_10m_max,precipitation_sum,sunrise,sunset";
    private static final String HOURLY_PARAMS = "temperature_2m,apparent_temperature,relative_humidity_2m,wind_speed_10m,cloud_cover,weather_code,precipitation,rain,showers,snowfall,visibility,wind_gusts_10m,wind_direction_10m,pressure_msl,surface_pressure,is_day";
    private static final String CURRENT_PARAMS = "relative_humidity_2m,is_day,apparent_temperature,temperature_2m,pressure_msl,cloud_cover,weather_code,surface_pressure,wind_gusts_10m,wind_speed_10m,wind_direction_10m";
    private final RestTemplate restTemplate;

    public OpenMeteoWeatherApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherData fetchWeatherData(WeatherRequest request) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("latitude", request.latitude)
                .queryParam("longitude", request.longitude)
                .queryParam("daily", DAILY_PARAMS)
                .queryParam("hourly", HOURLY_PARAMS)
                .queryParam("current", CURRENT_PARAMS)
                .queryParam("forecast_days", request.forecastDays)
                .queryParam("past_days", request.pastDays)
                .queryParam("timezone", "auto")
                .toUriString();
        return restTemplate.getForObject(url, WeatherData.class);
    }
}
