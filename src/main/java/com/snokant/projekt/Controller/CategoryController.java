package com.snokant.projekt.Controller;

import com.snokant.projekt.Model.Category;
import com.snokant.projekt.Service.CategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/category/")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("allCategories")
    public List<Category> allCategories() {
        return categoryService.getAllCategories();
    }
}

