package com.KitchenIQ.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "ingredient", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
    
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    //kg, litre, piece, gram
    @Column(nullable = false)
    private String unit;

    private boolean active = true;

    private LocalDateTime createdAt;


    //=====================
    //Constructors
    //=====================

    public Ingredient(){
        this.createdAt = LocalDateTime.now();
    }

    public Ingredient(String name, String unit){
        this.name = name;
        this.unit = unit;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    //================================
    //Getters and Setters
    //================================

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getUnit(){
        return unit;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
}
