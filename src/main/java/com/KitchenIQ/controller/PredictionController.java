package com.KitchenIQ.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.KitchenIQ.dto.PredictionResponseDTO;
import com.KitchenIQ.service.PredictionService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/demand")
    public List<PredictionResponseDTO> getDemandPrediction() {
        return predictionService.predictDemandForToday();
    }

    @GetMapping("/insights")
    public Map<String, String> getInsights() {
        return predictionService.getInsights();
    }

    @GetMapping("/prep-time")
    public Map<String, Integer> getEstimatedPrepTime(@RequestParam int currentOrders) {
        int prepTime = predictionService.predictPrepTime(currentOrders);
        return Map.of("estimatedPrepTime", prepTime);
    }

    @GetMapping("/surge-pricing")
    public Map<String, Double> getSurgePricingMultiplier(@RequestParam int currentOrders) {
        double multiplier = predictionService.calculateDynamicSurgeMultiplier(currentOrders);
        return Map.of("surgeMultiplier", multiplier);
    }
}
