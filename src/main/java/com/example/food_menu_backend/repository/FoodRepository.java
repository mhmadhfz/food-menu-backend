package com.example.food_menu_backend.repository;

import com.example.food_menu_backend.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategory(String category);
}
