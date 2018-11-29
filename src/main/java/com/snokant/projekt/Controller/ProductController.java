package com.snokant.projekt.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Service.CategoryService;
import com.snokant.projekt.Service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
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

    @GetMapping("get/{category}/{x}")
    public List<Product> fiveProductsByCategory(@PathVariable int x, @PathVariable String category) {
        return productService.getXProductsByCategory(x, category);
    }

    //    @PostMapping("addProductWithoutImage")
//    public List<String> addProductWithoutImage(@Valid @RequestBody Product product, BindingResult bindingResult) {
//        return productService.addProductWithoutImage(product, bindingResult);
//    }
    @PostMapping("addProduct")
    public List<String> addProductWithImage(
            @RequestPart("image") MultipartFile file,
            @RequestPart("product") String product,
            BindingResult bindingResult,
            @RequestHeader("Authorization") String token
    ) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Product p = mapper.readValue(product, Product.class);
            return productService.addProduct(p, bindingResult, file, token);
        } catch (Exception e) {
            return Arrays.asList("BLAD","Wartość pola przekracza długość!");
        }
    }

    @GetMapping("getNewestProducts/{x}")
    public List<Product> findXNewestProducts(@PathVariable int x) {
        return productService.findXNewestProducts(x);
    }

    @GetMapping("getNewestProducts/{category}/{x}")
    public List<Product> findXNewestProductsByCategory(@PathVariable int x, @PathVariable String category) {
        return productService.findXNewestProductsByCategory(x, category);
    }


}
