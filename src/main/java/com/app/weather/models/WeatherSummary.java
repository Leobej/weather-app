package com.app.weather.models;

import java.util.List;

public class WeatherSummary {
    // Current weather
    public String time; // e.g. "2025-06-14T14:00"

    public double temperature;
    public double apparentTemperature;
    public int humidity;

    public double pressureSeaLevel;
    public double pressureSurface;

    public int cloudCover;
    public int weatherCode;

    public double windSpeed;
    public double windGusts;
    public int windDirection;

    public double precipitation;
    public double rain;
    public double showers;
    public double snowfall;

    public double visibility;
    public int isDay;

    // Hourly forecast (today only)
    public List<HourlyForecast> hourlyForecast;

    // Daily forecast (up to 16 days)
    public List<DailyForecast> dailyForecast;

    public List<DailyForecast> pastForecast; // Historical data for past days

    public static class HourlyForecast {
        public String time;
        public double temperature;
        public double apparentTemperature;
        public int humidity;
        public double windSpeed;
        public int cloudCover;
        public int weatherCode;
        public double precipitation;
    }

    public static class DailyForecast {
        public String date;
        public double tempMin;
        public double tempMax;
        public double apparentTempMin;
        public double apparentTempMax;
        public int weatherCode;
        public double windSpeedMax;
        public double windGustsMax;
        public double precipitationSum;
        public String sunrise;
        public String sunset;
    }
}
