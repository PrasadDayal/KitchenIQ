package com.KitchenIQ.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.KitchenIQ.model.Ingredient;
import com.KitchenIQ.repository.IngredientRepository;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    //=================================
    //Add Ingredient
    //=================================

    public Ingredient saveIngredient(Ingredient ingredient){
        ingredientRepository.findByName(ingredient.getName())
        .ifPresent(existing ->{
            throw new RuntimeException("Ingredient already exist with name : " + ingredient.getName());
        });

        if (ingredient.getUnit() == null || ingredient.getUnit().isEmpty()) {
            throw new RuntimeException("Unit must not be empty");
        }

        return ingredientRepository.save(ingredient);
    }

    //=================================
    //Get all ingredient
    //=================================
    public List<Ingredient> getAllIngredient(){
        return ingredientRepository.findAll();
    }

    //=================================
    //Get only active ingredient
    //=================================
    public List<Ingredient> getActivIngredients(){
        return ingredientRepository.findByActiveTrue();
    }

    //=================================
    //Get ingredient by id
    //=================================
    public Ingredient getIngredientById(Long id){
        return ingredientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ingredient not found with id : " + id));
    }

    //=================================
    //Delete ingredient by id
    //=================================
    public void deleteIngredientById(Long id){
        Ingredient ingredient = getIngredientById(id);
        ingredient.setActive(false);
        ingredientRepository.save(ingredient);
    }
}
