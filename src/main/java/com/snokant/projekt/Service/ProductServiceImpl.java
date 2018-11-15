package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.Category;
import com.snokant.projekt.Repository.CategoryRepository;
import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Repository.ProductRepository;
import com.snokant.projekt.Repository.RoleRepository;
import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Repository.UserRepository;
import com.snokant.projekt.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @Override
    public List<Product> getAllByCategory(String category_name) {
        return productRepository.findProductsByCategoryName(category_name);
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    @Override
    public List<Product> searchByPhrase(String phrase) {
        return productRepository.findProductByNameContaining(phrase);
    }

    @Override
    public List<Product> getXProductsByCategory(Long x, String category) {
        return productRepository.findXRandomProducts(x, category);
    }









}
