package com.KitchenIQ.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory", uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "ingredient_id"}))
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id")
    private  Restaurant restaurant;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(nullable = false)
    private double currentStock;

    @Column(nullable = false)
    private double minimumStock;

    private LocalDateTime lastUpdated;

    @Column(name = "expiration_date")
    private java.time.LocalDate expirationDate;

    //==================================
    //Constructor
    //==================================

    public Inventory(){
        this.lastUpdated = LocalDateTime.now();
    }

    public Inventory(Restaurant restaurant, Ingredient ingredient, double currentStock, double minimumStock){
        this.restaurant = restaurant;
        this.ingredient = ingredient;
        this.currentStock = currentStock;
        this.minimumStock = minimumStock;
        this.lastUpdated = LocalDateTime.now();
    }

    //==================================
    // Getter & Setter
    //==================================

    public Long getId(){
        return id;
    }

    public Restaurant getRestaurant(){
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    public Ingredient getIngredient(){
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient){
        this.ingredient = ingredient;
    }

    public double getCurrentStock(){
        return currentStock;
    }

    public void setCurrentStock(double currentStock){
        this.currentStock = currentStock;
        this.lastUpdated = LocalDateTime.now();
    }

    public double getMinimumStock(){
        return minimumStock;
    }

    public void setMinimumStock(double minimumStock){
        this.minimumStock = minimumStock;
    }

    public LocalDateTime getLastUpdated(){
        return lastUpdated;
    }

    public java.time.LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(java.time.LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
