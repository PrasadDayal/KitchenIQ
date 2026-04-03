package com.KitchenIQ.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "food_item")
public class FoodItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String category;

    @Column(nullable = false)
    private double price;

    @Column(name = "cost_price", nullable = false)
    private double costPrice = 0.0;

    private boolean available = true;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = true)
    private Restaurant restaurant;

    /**************************** 
            Constructor
    ****************************/

    public FoodItem(){
        this.createdAt = LocalDateTime.now();
    }

    public FoodItem(String name, String category, double price, double costPrice, Restaurant restaurant){
        this.name = name;
        this.category = category;
        this.price = price;
        this.costPrice = costPrice;
        this.restaurant = restaurant;
        this.available = true;
        this.createdAt = LocalDateTime.now();
    }

    /****************************
            Getters and Setters
    ****************************/

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public Restaurant getRestaurant(){
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }
}
