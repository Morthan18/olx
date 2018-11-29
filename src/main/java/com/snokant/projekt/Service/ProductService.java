package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Domain.User;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;



public interface ProductService {
    List<Product> getAllByCategory(String category_name);
    Optional<Product> getProductById(Long id);
    List<Product> searchByPhrase(String phrase);
    List<Product> getXProductsByCategory(int x, String category);
    List<Product> findXNewestProducts(int x);
    List<Product> findXNewestProductsByCategory(int x,String category);
    List<String> addProduct(Product product, BindingResult bindingResult, MultipartFile file, String token);




}
