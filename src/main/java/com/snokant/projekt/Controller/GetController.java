package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Service.CategoryService;
import com.snokant.projekt.Service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest")
public class GetController {
    private ProductService productService;
    private CategoryService categoryService;

    public GetController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("product/productById/{id}")
    public Optional<Product> productByName(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("product/allProductsByCategory/{category_name}")
    public List<Product> allProductsByCategory(@PathVariable String category_name) {
        return productService.getAllByCategory(category_name);
    }

    @GetMapping("product/get/{x}/{category}")
    public List<Product> fiveProductsByCategory(@PathVariable long x, @PathVariable String category) {
        return productService.getXProductsByCategory(x, category);
    }

    @GetMapping("category/allCategories")
    public List<Category> allCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("search/{phrase}")
    public List<Product> searchByPhrase(@PathVariable String phrase) {
        return productService.searchByPhrase(phrase);
    }



}
