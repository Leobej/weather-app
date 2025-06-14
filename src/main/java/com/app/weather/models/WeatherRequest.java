package com.app.weather.models;

public class WeatherRequest {
    public double latitude;
    public double longitude;
    public int forecastDays = 1; // default to 1
    public int pastDays = 0;     // default to 0 (no history)

}

