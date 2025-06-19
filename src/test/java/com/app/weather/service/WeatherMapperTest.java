package com.app.weather.service;

import com.app.weather.models.WeatherData;
import com.app.weather.models.WeatherSummary;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class WeatherMapperTest {
    private final WeatherMapper mapper = new WeatherMapper();

    @Test
    void toWeatherSummary_mapsFieldsCorrectly() {
        WeatherData data = new WeatherData();
        data.current = new WeatherData.Current();
        data.current.time = "2025-06-14T14:00";
        data.current.temperature_2m = 25.0;
        data.current.apparent_temperature = 27.0;
        data.current.relative_humidity_2m = 60;
        data.current.pressure_msl = 1012.0;
        data.current.surface_pressure = 1010.0;
        data.current.cloud_cover = 80;
        data.current.weather_code = 2;
        data.current.wind_speed_10m = 5.0;
        data.current.wind_gusts_10m = 8.0;
        data.current.wind_direction_10m = 180;
        data.current.precipitation = 0.5;
        data.current.rain = 0.3;
        data.current.showers = 0.1;
        data.current.snowfall = 0.0;
        data.current.is_day = 1;
        data.hourly = new WeatherData.Hourly();
        data.hourly.time = Collections.singletonList("2025-06-14T14:00");
        data.hourly.temperature_2m = Collections.singletonList(25.0);
        data.hourly.apparent_temperature = Collections.singletonList(27.0);
        data.hourly.relative_humidity_2m = Collections.singletonList(60);
        data.hourly.wind_speed_10m = Collections.singletonList(5.0);
        data.hourly.weather_code = Collections.singletonList(2);
        data.hourly.cloud_cover = Collections.singletonList(80);
        data.hourly.precipitation = Collections.singletonList(0.5);
        data.daily = new WeatherData.Daily();
        data.daily.time = Collections.singletonList("2025-06-14");
        data.daily.temperature_2m_min = Collections.singletonList(15.0);
        data.daily.temperature_2m_max = Collections.singletonList(28.0);
        data.daily.apparent_temperature_min = Collections.singletonList(16.0);
        data.daily.apparent_temperature_max = Collections.singletonList(29.0);
        data.daily.weather_code = Collections.singletonList(2);
        data.daily.wind_speed_10m_max = Collections.singletonList(10.0);
        data.daily.wind_gusts_10m_max = Collections.singletonList(15.0);
        data.daily.precipitation_sum = Collections.singletonList(1.0);
        data.daily.sunrise = Collections.singletonList("05:00");
        data.daily.sunset = Collections.singletonList("21:00");

        WeatherSummary summary = mapper.toWeatherSummary(data, 1, 0);
        assertNotNull(summary);
        assertEquals("2025-06-14T14:00", summary.time);
        assertEquals(25.0, summary.temperature);
        assertEquals(27.0, summary.apparentTemperature);
        assertEquals(60, summary.humidity);
        assertEquals(1012.0, summary.pressureSeaLevel);
        assertEquals(1010.0, summary.pressureSurface);
        assertEquals(80, summary.cloudCover);
        assertEquals(2, summary.weatherCode);
        assertEquals(5.0, summary.windSpeed);
        assertEquals(8.0, summary.windGusts);
        assertEquals(180, summary.windDirection);
        assertEquals(0.5, summary.precipitation);
        assertEquals(0.3, summary.rain);
        assertEquals(0.1, summary.showers);
        assertEquals(0.0, summary.snowfall);
        assertEquals(1, summary.isDay);
        assertNotNull(summary.hourlyForecast);
        assertEquals(1, summary.hourlyForecast.size());
        assertNotNull(summary.dailyForecast);
        assertEquals(1, summary.dailyForecast.size());
    }
}

