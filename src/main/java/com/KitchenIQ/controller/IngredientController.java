package com.KitchenIQ.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KitchenIQ.model.Ingredient;
import com.KitchenIQ.service.IngredientService;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {
    
    private final IngredientService ingredientService;

    public IngredientController (IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public Ingredient addIngredient(@RequestBody Ingredient ingredient){
        return ingredientService.saveIngredient(ingredient);
    }

    @GetMapping
    public List<Ingredient> getAllIngredient(){
        return ingredientService.getAllIngredient();
    }
}

