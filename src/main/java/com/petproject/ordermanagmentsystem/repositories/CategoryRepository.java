package com.petproject.ordermanagmentsystem.repositories;

import com.petproject.ordermanagmentsystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
