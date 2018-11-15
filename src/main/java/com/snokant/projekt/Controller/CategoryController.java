package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Repository.UserRepository;
import com.snokant.projekt.Service.CategoryService;
import com.snokant.projekt.Configuration.SessionUser;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest")
public class CategoryController {
    CategoryService categoryService;
    UserRepository userRepository;

    public CategoryController(CategoryService categoryService, UserRepository userRepository) {
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    @GetMapping("category/allCategories")
    public List<Category> allCategories() {
        return categoryService.getAllCategories();
    }
//    @GetMapping("/token")
//    public Map<String,String> token(HttpSession session) {
//        return Collections.singletonMap("token", session.getId());
//    }


}

