package com.KitchenIQ.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KitchenIQ.model.FoodItem;
import com.KitchenIQ.repository.FoodItemRepository;

@Service
public class FoodItemService {
    private final FoodItemRepository foodItemRepository;

    public FoodItemService(FoodItemRepository foodItemRepository){
        this.foodItemRepository = foodItemRepository; 
    }

    //===========================
        //Save Food Item
    //===========================
    public FoodItem saveFoodItem(FoodItem foodItem){
        if (foodItem.getPrice() <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }
        if (foodItem.getCostPrice() < 0) {
            throw new RuntimeException("Cost Price cannot be negative");
        }
        return foodItemRepository.save(foodItem);
    }

    //===========================
        //Get All
    //===========================
    public List<FoodItem> getAllFoodItems(){
        return foodItemRepository.findAll();
    }

    //===========================
        //Get Food by ID
    //===========================
    public FoodItem getFoodItemById(Long id){
        return foodItemRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("Food Item not found with id:" + id));
    }

    // =========================
        // Get Only Available Items
    // =========================
    public List<FoodItem> getAvailaFoodItems(){
        return foodItemRepository.findByAvailableTrue();
    }

    // =========================
    // Get Food Items By Restaurant
    // =========================
    public List<FoodItem> getFoodByRestaurant(Long restaurantId){
        return foodItemRepository.findByRestaurantId(restaurantId);
    }

    // =========================
    // Delete Food Item
    // =========================
    public void deleteFoodItem(Long id){
        if (!foodItemRepository.existsById(id)){
            throw new RuntimeException("Food Item not found with id: " +id);
            
        }
        foodItemRepository.deleteById(id);
    }

}
