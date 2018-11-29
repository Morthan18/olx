package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Repository.UserRepository;
import com.snokant.projekt.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/category/")
public class CategoryController {
    CategoryService categoryService;
    UserRepository userRepository;

    public CategoryController(CategoryService categoryService, UserRepository userRepository) {
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    @GetMapping("allCategories")
    public List<Category> allCategories() {
        return categoryService.getAllCategories();
    }
//    @GetMapping("/token")
//    public Map<String,String> token(HttpSession session) {
//        return Collections.singletonMap("token", session.getId());
//    }


}

