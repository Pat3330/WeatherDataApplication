package com.example.weatherdataapplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class WeatherLoader {
    public static List<WeatherRecord> loadWeatherData() throws Exception{
        InputStream inputStream = WeatherLoader.class.getResourceAsStream("/com/example/weatherdataapplication/weatherData.csv");

        if (inputStream == null) {
            throw new Exception("File not found");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
            return reader.lines()
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(arr -> new WeatherRecord(
                            arr[0],
                            Double.parseDouble(arr[1]),
                            Double.parseDouble(arr[2]),
                            Double.parseDouble(arr[3])
                    ))
                    .toList();
        }
    }
}
