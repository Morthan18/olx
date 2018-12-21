package com.snokant.projekt.Controller;

import com.snokant.projekt.Model.Category;
import com.snokant.projekt.Repository.UserRepository;
import com.snokant.projekt.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

