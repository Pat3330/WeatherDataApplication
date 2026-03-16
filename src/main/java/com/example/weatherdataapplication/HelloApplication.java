package com.example.weatherdataapplication;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private int thresholdAmount = 10;
    private List<WeatherRecord> weatherData = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception {
        weatherData = WeatherLoader.loadWeatherData();
        //------- Left side of the screen -------
        Button averageButton = new Button();
        averageButton.setText("Average for Month");
        averageButton.setPrefWidth(100);
        averageButton.setPrefHeight(40);

        Button thresholdButton = new Button();
        thresholdButton.setText("Threshold");
        thresholdButton.setPrefWidth(100);
        thresholdButton.setPrefHeight(40);

        Button getRainyDaysButton = new Button();
        getRainyDaysButton.setText("Rainy Days");
        getRainyDaysButton.setPrefWidth(100);
        getRainyDaysButton.setPrefHeight(40);

        TextField thresholdInput = new TextField();
        thresholdInput.setPromptText("Enter Threshold");
        thresholdInput.setPrefWidth(100);
        thresholdInput.setPrefHeight(20);

        Button setNewThresholdButton = new Button();
        setNewThresholdButton.setText("Set Threshold");
        setNewThresholdButton.setPrefWidth(100);
        setNewThresholdButton.setPrefHeight(20);

        Label currentThreshold = new Label("The Threshold is: " + thresholdAmount + ".");
        currentThreshold.setPrefWidth(150);
        currentThreshold.setPrefHeight(20);

        thresholdInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") && setNewThresholdButton.isPressed()){
                currentThreshold.setText("The threshold is: " + oldValue + ".");
            }
        });
        setNewThresholdButton.setOnAction(e -> {
            String threshold = thresholdInput.getText();
            if (threshold.isEmpty()) {
                System.out.println("Threshold is empty");
                return;
            }
            try {
                thresholdAmount = Integer.parseInt(threshold);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
                return;
            }

            currentThreshold.setText("The threshold is: " + String.valueOf(thresholdAmount) +".");

        });

        VBox vbox = new VBox(10, averageButton, thresholdButton, getRainyDaysButton, thresholdInput, setNewThresholdButton, currentThreshold);


        //------- Right side of the screen -------

        Label averageTextField = new Label();
        averageTextField.setPrefWidth(100);
        averageTextField.setPrefHeight(40);

        Label thresholdTextField = new Label();
        thresholdTextField.setPrefWidth(100);
        thresholdTextField.setPrefHeight(40);

        Label rainyDaysTextField = new Label();
        rainyDaysTextField.setPrefWidth(100);
        rainyDaysTextField.setPrefHeight(40);

        TextField monthInput = new TextField();
        monthInput.setPromptText("Enter Month");
        monthInput.setPrefWidth(100);
        monthInput.setPrefHeight(20);

        Button setNewMonthButton = new Button();
        setNewMonthButton.setText("Set Month");
        setNewMonthButton.setPrefWidth(100);
        setNewMonthButton.setPrefHeight(20);


        VBox TextFieldVBox = new VBox(15, averageTextField, thresholdTextField, rainyDaysTextField, monthInput, setNewMonthButton);
        HBox hbox = new HBox(30, vbox, TextFieldVBox);
        Pane root = new Pane(hbox);

        averageButton.setOnAction(e -> {
            String state = describeCategory(categeorize(getAverageForMonth(weatherData)));
            averageTextField.setText(getAverageForMonth(weatherData) + " " + state);
        });

        thresholdButton.setOnAction(e -> {
            thresholdTextField.setText(getDaysAboveThreshold(weatherData) + " days above the threshold");
        });

        getRainyDaysButton.setOnAction(e -> {
            long rainyDays = countRainyDays(weatherData);
            rainyDaysTextField.setText(rainyDays + " rainy days");
        });


        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Weather Data Application");
        stage.show();
    }

    private double getAverageForMonth(List<WeatherRecord> weatherData) {
        return weatherData.stream()
                .filter(r -> Integer.parseInt(r.date().substring(5, 7)) == 8)
                .mapToDouble(WeatherRecord::temperature)
                .average()
                .orElse(0);
    }

    private double getDaysAboveThreshold(List<WeatherRecord> weatherData) {
        return weatherData.stream()
                .filter(r -> r.temperature() > thresholdAmount)
                .count();
    }

    private String categeorize(double temp){
        return switch ((int) temp /10){
            case 3, 4 -> "Hot";
            case 2 -> "Warm";
            case 1 -> "Cold";
            default -> "Freezing";
        };
    }

    private String describeCategory(String category){
        return switch (category){
            case String s when s.equals("Hot") -> "It's hot!";
            case String s when s.equals("Warm") -> "It's warm!";
            case String s when s.equals("Cold") -> "It's cold!";
            case String s when s.equals("Freezing") -> "It's freezing!";
                default -> "Unknown category";
        };
    }

    private long countRainyDays(List <WeatherRecord> weatherData){
        return weatherData.stream()
                .filter(w -> w.precipitation() > 0)
                .count();
    }

}
