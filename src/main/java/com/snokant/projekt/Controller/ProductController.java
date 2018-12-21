package com.snokant.projekt.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snokant.projekt.Model.Product;
import com.snokant.projekt.Repository.ProductRepository;
import com.snokant.projekt.Service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/product/")
public class ProductController {
    private ProductService productService;
    private ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("productById/{id}")
    public Product productById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }
    @GetMapping("productByIdWithToken/{id}")
    public boolean productByIdWithToken(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String token) {
        return productService.getProductByIdWithToken(id,token);
    }

    @GetMapping("allProductsByCategory/{categoryId}")
    public List<Product> allProductsByCategory(@PathVariable("categoryId") Long category) {
        return productService.getAllProductsByCategory(category);
    }

    @GetMapping("get/{categoryId}/{howMany}/{actual}")
    public List<Product> XProductsByCategory(@PathVariable("categoryId") Long categoryId, @PathVariable("howMany") int howMany,@PathVariable("actual") int actual) {
        return productService.getXProductsByCategory(howMany, categoryId,actual);
    }

    @PostMapping("addProduct")
    public List<String> addProductWithImage(
            @RequestParam("image") MultipartFile file,
            @RequestPart("product") String product,
            @RequestHeader("Authorization") String token) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Product p = mapper.readValue(product, Product.class);
            return productService.addProduct(file,p,  token);
        } catch (Exception e) {
            return Arrays.asList("BLAD", "Blad serializacji: "+e.getMessage());
        }
    }

    @GetMapping("getNewestProducts/{x}")
    public List<Product> findXNewestProducts(@PathVariable int x) {
        return productService.findXNewestProducts(x);
    }

    @GetMapping("getNewestProducts/{category}/{x}")
    public List<Product> findXNewestProductsByCategory(@PathVariable int x, @PathVariable int category) {
        return productService.findXNewestProductsByCategory(x, category);
    }

    @GetMapping("getProductCount/{category}")
    public Long getProductCount(@PathVariable("category") Long category) {
        return productService.getProductCountByCategory(category);
    }

    @GetMapping("myProducts")
    public List<Product> myProducts(@RequestHeader("Authorization") String token) {
        return productService.findMyProducts(token);
    }

    @GetMapping("getProducts/{userId}")
    public List<Product> getUserProducts(@PathVariable("userId") int userId) {
        return productService.findUserProducts(userId);
    }

    @GetMapping("getProductDetails/{productId}")
    public Object checkIfCanModifyProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable("productId") long productId) {
        return productService.checkIfUserIsProductOwner(token, productId);
    }

    @PutMapping("modifyProduct/{productId}")
    public List<String> modifyProduct(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid Product product,
            @PathVariable("productId") long productId) {
        return productService.modify(token, product, productId);
    }
    @GetMapping("deleteProduct/{productId}")
    public List<String> deleteProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable("productId") int productId){
        return productService.deleteProduct(productId,token);
    }
    @GetMapping("getProductsByName/{name}")
    public List<Product> getProductsByPhrase(@PathVariable("name") String phrase){
        return productService.findByName(phrase);
    }
    @GetMapping("getProductCount")
    public int getProductCount(){
        return productService.getProductCount();
    }
}
