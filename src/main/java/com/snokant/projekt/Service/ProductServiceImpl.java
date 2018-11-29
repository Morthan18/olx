package com.snokant.projekt.Service;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.snokant.projekt.Configuration.JwtConfiguration.JwtConstants;
import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Repository.ProductRepository;
import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    public static final String imagesDirectoryPath = "C:/xampp/htdocs/erdupko zdjecia/";
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
        return productRepository.findXNewestProductsByCategory(x, category);
    }

    @Transactional
    @Override
    public List<String> addProduct(Product product, BindingResult bindingResult, MultipartFile file, String token) {
        ErrorChecker errorChecker = new ErrorChecker();
        List<String> errors = errorChecker.checkErrors(product, bindingResult);
        if (file == null) {
            errors.add("BLAD");
            errors.add("Blad z plikiem");
            return errors;
        }
        if (errors == null) {
            String s = savedFilePath(file);
            if (s != null) {
                User currentSessionUser = getCurrentSessionUser(token);
                product.setOwner_id(currentSessionUser.getUser_id());

                product.setImage(savedFilePath(file));

                productRepository.save(product);
                return Arrays.asList("GIT","Dodano produkt");
            }
        }
        return errors;
    }


    private String savedFilePath(MultipartFile multipartFile) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filenameWithDate = dateFormat.format(new Date()) + "~" + multipartFile.getOriginalFilename();
        String finalFileName = imagesDirectoryPath + filenameWithDate;

        File file = new File(finalFileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (IOException e) {
            return e.getMessage();
        }
        return finalFileName;
    }

    private User getCurrentSessionUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtConstants.SECRET)
                .parseClaimsJws(token).getBody();
        return userRepository.findUserByEmail(claims.get("email").toString());
    }


}
