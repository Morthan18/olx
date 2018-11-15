package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;


@Transactional(readOnly = true)
public interface ProductService {

    List<Product> getAllByCategory(String category_name);

    Optional<Product> getProductById(Long id);

    List<Product> searchByPhrase(String phrase);

    List<Product> getXProductsByCategory(Long x, String category);





}
