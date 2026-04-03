package com.KitchenIQ.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.KitchenIQ.dto.OrderRequestDTO;
import com.KitchenIQ.model.OrderHistory;
import com.KitchenIQ.service.OrderService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ CREATE Order
    @PostMapping
    public OrderHistory createOrder(@RequestBody OrderRequestDTO orderDTO) {
        return orderService.createOrderFromDTO(orderDTO);
    }

    // ✅ GET All Orders
    @GetMapping
    public List<OrderHistory> getAllOrders() {
        return orderService.getAllOrders();
    }

    // ✅ UPDATE Order Status
    @PutMapping("/{id}/status")
    public OrderHistory updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    // ✅ DELETE Order
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}