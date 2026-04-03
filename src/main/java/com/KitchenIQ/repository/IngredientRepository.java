package com.KitchenIQ.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KitchenIQ.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    
    //Find ingredient by name
    Optional<Ingredient> findByName(String name);

    //Get only active ingredients
    List<Ingredient> findByActiveTrue();

}
