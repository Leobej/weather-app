package com.app.weather.service;

import com.app.weather.models.WeatherData;
import com.app.weather.models.WeatherSummary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherMapper {
    public WeatherSummary toWeatherSummary(WeatherData data, int forecastDays, int pastDays) {
        WeatherSummary summary = new WeatherSummary();
        summary.time = data.current.time;
        summary.temperature = data.current.temperature_2m;
        summary.apparentTemperature = data.current.apparent_temperature;
        summary.humidity = data.current.relative_humidity_2m;
        summary.pressureSeaLevel = data.current.pressure_msl;
        summary.pressureSurface = data.current.surface_pressure;
        summary.cloudCover = data.current.cloud_cover;
        summary.weatherCode = data.current.weather_code;
        summary.windSpeed = data.current.wind_speed_10m;
        summary.windGusts = data.current.wind_gusts_10m;
        summary.windDirection = data.current.wind_direction_10m;
        summary.precipitation = data.current.precipitation;
        summary.rain = data.current.rain;
        summary.showers = data.current.showers;
        summary.snowfall = data.current.snowfall;
        summary.isDay = data.current.is_day;

        // Map hourlyForecast (today only)
        String today = data.current.time.substring(0, 10);
        List<WeatherSummary.HourlyForecast> hourlyList = new ArrayList<>();
        for (int i = 0; i < data.hourly.time.size(); i++) {
            if (data.hourly.time.get(i).startsWith(today)) {
                WeatherSummary.HourlyForecast hf = new WeatherSummary.HourlyForecast();
                hf.time = data.hourly.time.get(i);
                hf.temperature = data.hourly.temperature_2m.get(i);
                hf.apparentTemperature = data.hourly.apparent_temperature.get(i);
                hf.humidity = data.hourly.relative_humidity_2m.get(i);
                hf.windSpeed = data.hourly.wind_speed_10m.get(i);
                hf.weatherCode = data.hourly.weather_code.get(i);
                hf.cloudCover = data.hourly.cloud_cover.get(i);
                hf.precipitation = data.hourly.precipitation.get(i);
                hourlyList.add(hf);
            }
        }
        summary.hourlyForecast = hourlyList;

        // Map dailyForecast (future days)
        List<WeatherSummary.DailyForecast> dailyList = new ArrayList<>();
        int offset = pastDays;
        for (int i = offset; i < offset + forecastDays && i < data.daily.time.size(); i++) {
            dailyList.add(mapDaily(data, i));
        }
        summary.dailyForecast = dailyList;

        // Map pastForecast (past days)
        List<WeatherSummary.DailyForecast> pastList = new ArrayList<>();
        for (int i = 0; i < pastDays && i < data.daily.time.size(); i++) {
            pastList.add(mapDaily(data, i));
        }
        summary.pastForecast = pastList;

        return summary;
    }

    private WeatherSummary.DailyForecast mapDaily(WeatherData data, int i) {
        WeatherSummary.DailyForecast df = new WeatherSummary.DailyForecast();
        df.date = data.daily.time.get(i);
        df.tempMin = data.daily.temperature_2m_min.get(i);
        df.tempMax = data.daily.temperature_2m_max.get(i);
        df.apparentTempMin = data.daily.apparent_temperature_min.get(i);
        df.apparentTempMax = data.daily.apparent_temperature_max.get(i);
        df.weatherCode = data.daily.weather_code.get(i);
        df.windSpeedMax = data.daily.wind_speed_10m_max.get(i);
        df.windGustsMax = data.daily.wind_gusts_10m_max.get(i);
        df.precipitationSum = data.daily.precipitation_sum.get(i);
        df.sunrise = data.daily.sunrise.get(i);
        df.sunset = data.daily.sunset.get(i);
        return df;
    }
}
