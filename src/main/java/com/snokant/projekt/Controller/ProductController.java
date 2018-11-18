package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Service.CategoryService;
import com.snokant.projekt.Service.ProductService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping("get{x}/{category}")
    public List<Product> fiveProductsByCategory(@PathVariable int x, @PathVariable String category) {
        return productService.getXProductsByCategory(x, category);
    }

    @PostMapping("addProductWithoutImage")
    public List<String> addProductWithoutImage(@Valid @RequestBody Product product, BindingResult bindingResult) {
        return productService.addProductWithoutImage(product, bindingResult);
    }
    @PostMapping("addProductWithImage")
    public List<String> addProductWithImage(
            @RequestParam("image")MultipartFile file,
            @RequestParam("product") Product product, BindingResult bindingResult ) {
        return productService.addProductWithImage(product, bindingResult,file);
    }
    @PostMapping("dodaj")
    public String asa(@RequestParam("image") MultipartFile file) {
        return productService.addImage(file);
    }

    @GetMapping("get{x}NewestProducts")
    public List<Product> findXNewestProducts(@PathVariable int x) {
        return productService.findXNewestProducts(x);
    }
    @GetMapping("get{x}NewestProducts/{category}")
    public List<Product> findXNewestProductsByCategory(@PathVariable int x,@PathVariable String category) {
        return productService.findXNewestProductsByCategory(x,category);
    }


}
