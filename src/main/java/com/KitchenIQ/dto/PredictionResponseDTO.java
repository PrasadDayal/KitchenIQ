package com.KitchenIQ.dto;

import java.util.Map;

public class PredictionResponseDTO {
    private String itemName;
    private double predictedQuantity;
    private String dayOfWeek;
    private String message;
    private Map<String, Double> hourlyForecast;

    public PredictionResponseDTO() {}

    public PredictionResponseDTO(String itemName, double predictedQuantity, String dayOfWeek, String message) {
        this.itemName = itemName;
        this.predictedQuantity = predictedQuantity;
        this.dayOfWeek = dayOfWeek;
        this.message = message;
    }

    // Getters and Setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPredictedQuantity() {
        return predictedQuantity;
    }

    public void setPredictedQuantity(double predictedQuantity) {
        this.predictedQuantity = predictedQuantity;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Double> getHourlyForecast() {
        return hourlyForecast;
    }

    public void setHourlyForecast(Map<String, Double> hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }
}
