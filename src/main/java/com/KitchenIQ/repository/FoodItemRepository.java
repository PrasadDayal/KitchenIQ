package com.KitchenIQ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KitchenIQ.model.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long>{
    
    //Get all food items of Specific restaurant
    List<FoodItem> findByRestaurantId(Long restaurantId);

    //Get only available items
    List<FoodItem> findByAvailableTrue();

    //Search by category
    List<FoodItem> findByCategory(String category);

    // Find by name
    java.util.Optional<FoodItem> findByName(String name);
}
