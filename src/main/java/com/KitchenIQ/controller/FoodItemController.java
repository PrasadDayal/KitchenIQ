package com.KitchenIQ.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KitchenIQ.model.FoodItem;
import com.KitchenIQ.service.FoodItemService;

@RestController
@RequestMapping("api/food-items")
public class FoodItemController {
    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService){
        this.foodItemService = foodItemService;
    }

    @PostMapping
    public FoodItem addFoodItem(@RequestBody FoodItem foodItem){
        return foodItemService.saveFoodItem(foodItem);
    }

    @GetMapping
    public List<FoodItem> getAllFoodItems(){
        return foodItemService.getAllFoodItems();
    }

    @GetMapping("/{id}")
    public FoodItem getFoodItem(@PathVariable Long id){
        return foodItemService.getFoodItemById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteFoodItem(@PathVariable Long id){
        foodItemService.deleteFoodItem(id);
        return "Food Item Delete Sucessfully";
    }
}
