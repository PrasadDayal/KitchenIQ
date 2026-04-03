package com.KitchenIQ.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KitchenIQ.model.Inventory;
import com.KitchenIQ.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public Inventory addInventory(@RequestBody Inventory inventory){
        return inventoryService.saveInventory(inventory);
    }

    @GetMapping
    public List<Inventory> getAllInventory(){
        return inventoryService.getAllInventory();
    }

    @GetMapping("/low-Stock")
    public List<Inventory> getLowStock(){
        return inventoryService.getLowStockItems();
    }

    @GetMapping("/expiring")
    public List<Inventory> getExpiringInventory(){
        return inventoryService.getExpiringInventory(3); // Default 3 days threshold
    }
}
