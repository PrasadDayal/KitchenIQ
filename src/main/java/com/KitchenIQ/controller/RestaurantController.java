package com.KitchenIQ.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KitchenIQ.model.Restaurant;
import com.KitchenIQ.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public Restaurant addRestaurant(@RequestBody Restaurant restaurant){
        return restaurantService.saveRestaurant(restaurant);
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id){
        return restaurantService.getRestaurantById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteRestaurant(@PathVariable Long id){
        restaurantService.deleteRestaurant(id);
        return "Restaurant Deleted Successfully";
    }
}
