package com.KitchenIQ.service;

import com.KitchenIQ.model.OrderHistory;
import com.KitchenIQ.repository.OrderHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository){
        this.orderHistoryRepository = orderHistoryRepository;
    }

    //=================================
    //Place Order
    //=================================

    public OrderHistory saveOrder(OrderHistory order){
        if (order.getQuantity() < 0){
            throw new RuntimeException("Quantity must be greater than 0");
        }

        //totalPrice automatically calculated in constructor

        return orderHistoryRepository.save(order);
    }

    //==================================
    //Get All Orders
    //==================================

    public List<OrderHistory> getAllOrders(){
        return orderHistoryRepository.findAll();
    }

    //=================================
    //Get Order By Restaurant
    //=================================
    public List<OrderHistory> getOrdersByRestaurant(Long restaurantId){
        return orderHistoryRepository.findByRestaurantId(restaurantId);
    }

    //==================================
    //Get orders by Food Item
    //==================================
    public List<OrderHistory> getOrdersByFoodItem(Long foodItemId){
        return orderHistoryRepository.findByFoodItemId(foodItemId);
    }

    //=================================
    //Get Orders Last N Days(for Predictions)
    //================================
    public List<OrderHistory> getOrdersLastNDays(Long foodItemId, int days){
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().minusDays(days);

        return orderHistoryRepository
                .findByFoodItemIdAndOrderDateBetween(foodItemId, startDate, endDate);
    }
}
