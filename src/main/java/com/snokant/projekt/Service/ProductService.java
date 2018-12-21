package com.snokant.projekt.Service;

import com.snokant.projekt.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.lang.Math.min;


public interface ProductService {
    List<Product> getAllProductsByCategory(Long category);

    Product getProductById(Long id);

    List<String> findProductNameByNames(String phrase);

    List<Product> getXProductsByCategory(int x, Long category,int y);

    List<Product> findXNewestProducts(int x);

    List<Product> findXNewestProductsByCategory(int x, int category);

    List<String> addProduct(MultipartFile file,Product product, String token);

    List<Product> findMyProducts(String token);

    List<Product> findUserProducts(int userId);

    Object checkIfUserIsProductOwner(String token, long productId);

    List<String> modify(String token, Product product, long productId);

    Long getProductCountByCategory(Long category);

    List<String> deleteProduct(long productId, String token);

    List<Product> findByName(String phrase);

    int getProductCount();

    boolean getProductByIdWithToken(Long id, String token);
}
