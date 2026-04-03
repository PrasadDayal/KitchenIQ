package com.KitchenIQ.repository;

import com.KitchenIQ.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    //Find inventory by Restaurant
    List<Inventory> findByRestaurantId(Long restaurantId);

    //Find by ingredient + restaurant
    Optional<Inventory> findByRestaurantIdAndIngredientId(Long restaurantId, Long ingredientId);

    List<Inventory> findByCurrentStockLessThanEqual(double minimumStock);
}
