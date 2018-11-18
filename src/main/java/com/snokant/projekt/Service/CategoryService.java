package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
}
