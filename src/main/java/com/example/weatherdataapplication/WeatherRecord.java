package com.example.weatherdataapplication;

public record WeatherRecord (
        String date,
        double temperature,
        double humidity,
        double precipitation
) {}
