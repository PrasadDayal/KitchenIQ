package com.KitchenIQ.service;

import com.KitchenIQ.model.Inventory;
import com.KitchenIQ.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    //====================================
    //Add Inventory Entry
    //====================================

    public Inventory saveInventory(Inventory inventory){
        if (inventory.getCurrentStock() < 0) {
            throw new RuntimeException("Current Stock cannot be negative");
        }

        if (inventory.getMinimumStock() < 0){
            throw new RuntimeException("Minimum Stock cannot be Negative");
        }

        return inventoryRepository.save(inventory);

    }

    //====================================
    //Get All Inventory
    //====================================

    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }

    //====================================
    //Get By Id
    //====================================

    public Inventory getInventoryById(Long id){
        return inventoryRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Inventory not found with id: " + id));
    }

    //====================================
    //Get Inventory By Restaurant
    //====================================

    public List<Inventory> getInventoryByRestaurant(Long restaurantId){
        return inventoryRepository.findByRestaurantId(restaurantId);
    }

    //====================================
    //Update Stock
    //====================================

    public Inventory updateStock(Long inventoryId, double newStock){
        Inventory inventory = getInventoryById(inventoryId);

        if (newStock < 0){
            throw new RuntimeException("Stock cannot be Negative");
        }

        inventory.setCurrentStock(newStock);
        return inventoryRepository.save(inventory);
    }

    //====================================
    //Get Low Stock Items
    //====================================

    public List<Inventory> getLowStockItems(){
        List<Inventory> allInventory = inventoryRepository.findAll();

        return allInventory.stream()
                .filter(inv ->inv.getCurrentStock() <= inv.getMinimumStock())
                .toList();
    }

    //====================================
    //Get Expiring Items (Waste Reduction)
    //====================================

    public List<Inventory> getExpiringInventory(int daysThreshold){
        java.time.LocalDate thresholdDate = java.time.LocalDate.now().plusDays(daysThreshold);
        List<Inventory> allInventory = inventoryRepository.findAll();
        
        return allInventory.stream()
                .filter(inv -> inv.getExpirationDate() != null && !inv.getExpirationDate().isAfter(thresholdDate) && inv.getCurrentStock() > 0)
                .toList();
    }
}
