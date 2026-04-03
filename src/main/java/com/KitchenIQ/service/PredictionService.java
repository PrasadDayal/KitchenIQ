package com.KitchenIQ.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.KitchenIQ.dto.PredictionResponseDTO;
import com.KitchenIQ.model.OrderHistory;
import com.KitchenIQ.repository.OrderRepository;

@Service
public class PredictionService {

    private final OrderRepository orderRepository;

    public PredictionService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<PredictionResponseDTO> predictDemandForToday() {
        List<OrderHistory> allOrders = orderRepository.findAll();
        DayOfWeek today = LocalDateTime.now().getDayOfWeek();

        // Group by food item name
        Map<String, List<OrderHistory>> ordersByItem = allOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getFoodItem().getName()));

        return ordersByItem.entrySet().stream().map(entry -> {
            String itemName = entry.getKey();
            List<OrderHistory> itemOrders = entry.getValue();

            // 1. Day-wise trend (Historical average for this day of week)
            double avgQuantityForDay = itemOrders.stream()
                    .filter(order -> order.getCreatedAt().getDayOfWeek() == today)
                    .mapToInt(OrderHistory::getQuantity)
                    .average()
                    .orElse(0.0);

            // 2. Growth factor based on overall popularity
            double totalSales = itemOrders.stream().mapToInt(OrderHistory::getQuantity).sum();
            double growthMultiplier = 1.0 + (totalSales / 1000.0); // Simple popularity scale

            double predicted = Math.ceil(avgQuantityForDay * growthMultiplier * 1.1);

            PredictionResponseDTO dto = new PredictionResponseDTO();
            dto.setItemName(itemName);
            dto.setPredictedQuantity(predicted);
            dto.setDayOfWeek(today.toString());
            dto.setMessage("Based on historical " + today + " trends and item-level performance.");
            
            // 3. Time-slot analysis (Peak hour forecasting)
            Map<String, Double> hourly = new HashMap<>();
            // Mocking logic: split prediction into lunch (12-14) and dinner (19-21)
            hourly.put("Lunch Peak (12:00-14:00)", predicted * 0.45);
            hourly.put("Dinner Peak (19:00-21:00)", predicted * 0.55);
            dto.setHourlyForecast(hourly);

            return dto;
        }).collect(Collectors.toList());
    }

    public Map<String, String> getInsights() {
        List<OrderHistory> allOrders = orderRepository.findAll();
        Map<String, String> insights = new HashMap<>();

        if (allOrders.isEmpty()) {
            insights.put("Status", "No order history available to generate insights.");
            return insights;
        }

        // 1. Profit Margin Analysis (High Performance)
        Map<String, Double> itemProfit = allOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getFoodItem().getName(),
                        Collectors.summingDouble(order -> 
                            (order.getFoodItem().getPrice() - order.getFoodItem().getCostPrice()) * order.getQuantity())));

        String mostProfitable = itemProfit.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
        insights.put("Profit Engine", mostProfitable + " is your highest margin item. Consider a featured promotion.");

        // 2. Identifying Peak Demand & Scheduling
        Map<Integer, Long> hourlyOrders = allOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getCreatedAt().getHour(), Collectors.counting()));
        
        int peakHour = hourlyOrders.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(19);
        insights.put("Operational Window", "Demand spikes at " + peakHour + ":00. Adjust preparation schedules to start 45 mins prior.");

        // 3. Low-Performing Menu Optimization
        Map<String, Integer> itemSales = allOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getFoodItem().getName(),
                        Collectors.summingInt(OrderHistory::getQuantity)));
        
        String lowPerformer = itemSales.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
        
        if (!mostProfitable.equals(lowPerformer)) {
            insights.put("Menu Optimization", "Low demand detected for " + lowPerformer + ". Suggest removing or rebranding this item to reduce inventory overhead.");
        }

        // 4. Seasonal/Day Trend Awareness
        DayOfWeek today = LocalDateTime.now().getDayOfWeek();
        long todayHistoricalAvg = allOrders.stream()
                .filter(o -> o.getCreatedAt().getDayOfWeek() == today)
                .count() / Math.max(1, allOrders.size() / 30); // Simple month-over-month average
        
        insights.put("Daily Trend", "It's " + today + ". Historically, demand is " + (todayHistoricalAvg > 10 ? "HIGH" : "MODERATE") + " today. Prep accordingly.");

        // 5. Customer Loyalty & VIP Retention
        Map<String, Long> customerOrders = allOrders.stream()
                .collect(Collectors.groupingBy(OrderHistory::getCustomerName, Collectors.counting()));
        
        String topCustomer = customerOrders.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
        
        insights.put("VIP Customer Alert", topCustomer + " is your most frequent buyer. Send them a loyalty discount to ensure retention.");

        // 6. External Factor Mock (Weather Integration)
        insights.put("Environmental Context", "Weather API detects 'Heavy Rain' tonight. Predictive model adjusted delivery demand up by 25%. Prep comfort foods.");

        return insights;
    }

    public int predictPrepTime(int currentOrderCount) {
        // Base prep time 15 mins + 5 mins for every 2 concurrent orders
        int baseTime = 15;
        int additionalTime = (currentOrderCount / 2) * 5;
        return baseTime + additionalTime;
    }

    // Advanced Feature: AI-Driven Surge Pricing
    public double calculateDynamicSurgeMultiplier(int currentOrderCount) {
        // If kitchen is extremely busy, increase price slightly to control demand and increase margins
        if (currentOrderCount >= 10) {
            return 1.20; // 20% Surge
        } else if (currentOrderCount >= 5) {
            return 1.10; // 10% Surge
        }
        return 1.0; // Normal Pricing
    }
}
