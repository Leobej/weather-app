package com.app.weather.service;

import com.app.weather.models.WeatherData;
import com.app.weather.models.WeatherRequest;
import com.app.weather.models.WeatherSummary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class WeatherService {
    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherSummary getWeatherData(WeatherRequest request) {
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f" +
                        "&daily=apparent_temperature_max,apparent_temperature_min,temperature_2m_min,temperature_2m_max,weather_code," +
                        "uv_index_clear_sky_max,uv_index_max,sunshine_duration,sunset,daylight_duration,sunrise,precipitation_sum," +
                        "snowfall_sum,showers_sum,rain_sum,precipitation_probability_max,precipitation_hours,shortwave_radiation_sum," +
                        "wind_direction_10m_dominant,wind_speed_10m_max,wind_gusts_10m_max,et0_fao_evapotranspiration" +
                        "&hourly=temperature_2m,wind_speed_10m,rain,showers,snow_depth,snowfall,precipitation,precipitation_probability," +
                        "apparent_temperature,weather_code,visibility,cloud_cover_mid,cloud_cover_low,surface_pressure,pressure_msl," +
                        "cloud_cover,cloud_cover_high,dew_point_2m,relative_humidity_2m,evapotranspiration,et0_fao_evapotranspiration," +
                        "vapour_pressure_deficit,temperature_80m,temperature_180m,temperature_120m,wind_gusts_10m,wind_direction_180m," +
                        "wind_direction_120m,wind_direction_80m,wind_direction_10m,wind_speed_180m,wind_speed_120m,soil_temperature_6cm," +
                        "soil_temperature_0cm,soil_temperature_54cm,soil_temperature_18cm,wind_speed_80m,soil_moisture_1_to_3cm," +
                        "soil_moisture_3_to_9cm,soil_moisture_0_to_1cm,soil_moisture_9_to_27cm,soil_moisture_27_to_81cm" +
                        "&current=relative_humidity_2m,is_day,apparent_temperature,temperature_2m,precipitation,rain,showers,snowfall," +
                        "pressure_msl,cloud_cover,weather_code,surface_pressure,wind_gusts_10m,wind_speed_10m,wind_direction_10m" +
                        "&forecast_days=%d&past_days=%d&timezone=auto",
                request.latitude, request.longitude, request.forecastDays, request.pastDays
        );

        WeatherData data = restTemplate.getForObject(url, WeatherData.class);

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

        // === visibility from hourly (match time) ===
        String roundedTime = data.current.time.substring(0, 13) + ":00"; // "2025-06-14T19:00"
        int index = data.hourly.time.indexOf(roundedTime);

        String today = data.current.time.substring(0, 10); // "2025-06-14"
        summary.hourlyForecast = new ArrayList<>();

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
                summary.hourlyForecast.add(hf);
            }
        }

        summary.dailyForecast = new ArrayList<>();
        int offset = request.pastDays;
        for (int i = offset; i < offset + request.forecastDays && i < data.daily.time.size(); i++) {
            summary.dailyForecast.add(mapDaily(data, i));
        }

        summary.pastForecast = new ArrayList<>();
        for (int i = 0; i < request.pastDays && i < data.daily.time.size(); i++) {
            summary.pastForecast.add(mapDaily(data, i));
        }

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