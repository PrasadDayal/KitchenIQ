package com.KitchenIQ.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private double totalPrice;

    private LocalDateTime createdAt;

    private LocalDate orderDate;

    @Column(nullable = false)
    private String status = "PENDING"; // NEW: status for throttling

    //=====================================
    //Constructor
    //=====================================

    public OrderHistory(){
        this.createdAt = LocalDateTime.now();
        this.orderDate = LocalDate.now();
        this.status = "PENDING";
    }

    public OrderHistory(Restaurant restaurant, FoodItem foodItem, int quantity){
        this.restaurant = restaurant;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.totalPrice = foodItem.getPrice() * quantity;
        this.orderDate = LocalDate.now();
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    //=======================================
    //Getter & Setter
    //=======================================

    public Long getId(){
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    public FoodItem getFoodItem(){
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem){
        this.foodItem = foodItem;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderDate(){
        return orderDate;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
