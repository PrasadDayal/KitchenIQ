package com.KitchenIQ.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KitchenIQ.dto.OrderRequestDTO;
import com.KitchenIQ.model.FoodItem;
import com.KitchenIQ.model.OrderHistory;
import com.KitchenIQ.model.Restaurant;
import com.KitchenIQ.repository.FoodItemRepository;
import com.KitchenIQ.repository.OrderRepository;
import com.KitchenIQ.repository.RestaurantRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final PredictionService predictionService;

    public OrderService(OrderRepository orderRepository, FoodItemRepository foodItemRepository, 
                        RestaurantRepository restaurantRepository, PredictionService predictionService) {
        this.orderRepository = orderRepository;
        this.foodItemRepository = foodItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.predictionService = predictionService;
    }

    public OrderHistory createOrderFromDTO(OrderRequestDTO dto) {
        // Throttling Check
        long activeOrders = orderRepository.countByStatus("PENDING");
        if (activeOrders >= 15) {
            throw new RuntimeException("RESTAURANT_OVERLOADED: Too many active orders. Current prep time: " + 
                predictionService.predictPrepTime((int)activeOrders) + " mins.");
        }

        // 1. Get or Create Restaurant first to avoid null constraint on FoodItem
        Restaurant restaurant = restaurantRepository.findAll().stream().findFirst()
                .orElseGet(() -> {
                    Restaurant newRes = new Restaurant();
                    newRes.setName("Default Restaurant");
                    return restaurantRepository.save(newRes);
                });

        // 2. Get or Create FoodItem and ensure it has a restaurant
        FoodItem foodItem = foodItemRepository.findByName(dto.getFoodItem())
                .orElseGet(() -> {
                    FoodItem newItem = new FoodItem();
                    newItem.setName(dto.getFoodItem());
                    newItem.setPrice(10.0);
                    newItem.setCostPrice(7.0); // Default margin of 3.0
                    newItem.setRestaurant(restaurant); // FIX: Assign restaurant before saving
                    return foodItemRepository.save(newItem);
                });

        // 3. Apply Surge Pricing based on current kitchen load
        double surgeMultiplier = predictionService.calculateDynamicSurgeMultiplier((int) activeOrders);
        double finalPrice = (foodItem.getPrice() * dto.getQuantity()) * surgeMultiplier;

        // 4. Create the Order
        OrderHistory order = new OrderHistory();
        order.setCustomerName(dto.getCustomerName());
        order.setFoodItem(foodItem);
        order.setRestaurant(restaurant);
        order.setQuantity(dto.getQuantity());
        order.setTotalPrice(finalPrice);
        order.setStatus("PENDING");

        // Simple Delivery Assignment logic (Mock)
        String deliveryBoy = (System.currentTimeMillis() % 2 == 0) ? "Delivery Boy A (Near)" : "Delivery Boy B (Near)";
        System.out.println("Assigned " + deliveryBoy + " to order for " + dto.getCustomerName());
        
        return orderRepository.save(order);
    }

    public List<OrderHistory> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderHistory getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderHistory updateOrderStatus(Long id, String status) {
        OrderHistory order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
