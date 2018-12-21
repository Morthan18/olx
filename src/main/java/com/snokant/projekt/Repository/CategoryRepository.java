package com.snokant.projekt.Repository;

import com.snokant.projekt.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
