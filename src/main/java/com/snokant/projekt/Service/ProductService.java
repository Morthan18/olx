package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;



public interface ProductService {
    @Transactional(readOnly = true)
    List<Product> getAllByCategory(String category_name);
    @Transactional(readOnly = true)
    Optional<Product> getProductById(Long id);
    @Transactional(readOnly = true)
    List<Product> searchByPhrase(String phrase);
    @Transactional(readOnly = true)
    List<Product> getXProductsByCategory(Long x, String category);
    @Transactional
    List<String> addProduct(Product product,BindingResult bindingResult);





}
