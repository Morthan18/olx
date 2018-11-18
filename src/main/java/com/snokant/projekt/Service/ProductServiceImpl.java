package com.snokant.projekt.Service;

import com.snokant.projekt.Configuration.SessionUser;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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
    public List<Product> getXProductsByCategory(int x, String category) {
        return productRepository.findXRandomProducts(x, category);
    }

    @Override
    public List<Product> findXNewestProducts(int x) {
        return productRepository.findXNewestProducts(x);
    }

    @Override
    public List<Product> findXNewestProductsByCategory(int x, String category) {
        return productRepository.findXNewestProductsByCategory(x,category);
    }

    @Transactional
    @Override
    public List<String> addProductWithoutImage(Product product, BindingResult bindingResult) {
        ErrorChecker errorChecker = new ErrorChecker();
        List<String> errors = errorChecker.checkErrors(product, bindingResult);
        if (errors == null) {
            User currentSessionUser = getCurrentSessionUser();
            if(currentSessionUser!=null){
                product.setOwner_id(currentSessionUser.getUser_id());
            }
            productRepository.save(product);
            return Arrays.asList("Dodano produkt");
        }
        return errors;
    }
    @Transactional
    @Override
    public List<String> addProductWithImage(Product product, BindingResult bindingResult,MultipartFile file) {
        ErrorChecker errorChecker = new ErrorChecker();
        List<String> errors = errorChecker.checkErrors(product, bindingResult);
        if(file==null) {
            return Arrays.asList("Blad z plikiem");
        }

        if (errors == null) {
            User currentSessionUser = getCurrentSessionUser();
            if(currentSessionUser!=null){
                product.setOwner_id(currentSessionUser.getUser_id());
            }
            product.setImage(addImage(file));
            productRepository.save(product);
            return Arrays.asList("Dodano produkt");
        }
        return errors;
    }

    private void checkIfFileIsEmpty(MultipartFile file) {

    }

    private final Path rootLocation = Paths.get("krzychokrzysxd");

    public String addImage(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "HUJNIA";
        }

        return "OK";
    }

    private User getCurrentSessionUser(){
        SessionUser sessionUser = new SessionUser(userRepository);
        return sessionUser.getCurrentUser();
    }


}
