package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Service.CategoryService;
import com.snokant.projekt.Service.ProductService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/product/")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("productById/{id}")
    public Optional<Product> productByName(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("allProductsByCategory/{category_name}")
    public List<Product> allProductsByCategory(@PathVariable String category_name) {
        return productService.getAllByCategory(category_name);
    }

    @GetMapping("get/{x}/{category}")
    public List<Product> fiveProductsByCategory(@PathVariable long x, @PathVariable String category) {
        return productService.getXProductsByCategory(x, category);
    }
    @PostMapping("addProduct")
    public List<String> addProduct(@Valid @RequestBody Product product, BindingResult bindingResult, HttpSession session) {

        return productService.addProduct(product,bindingResult);
    }







}
