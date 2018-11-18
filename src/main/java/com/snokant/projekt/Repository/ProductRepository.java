package com.snokant.projekt.Repository;

import com.snokant.projekt.Domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "SELECT * FROM products, categories WHERE products.category_id=categories.category_id AND categories.category_name= :category",nativeQuery = true)
    List<Product> findProductsByCategoryName(@Param("category") String category);
    List<Product> findProductByNameContaining(String phrase);
    @Query(value = "SELECT * FROM Products INNER JOIN categories ON products.category_id=categories.category_id where categories.category_name=:category1 ORDER BY RAND() LIMIT :x",nativeQuery = true)
    List<Product> findXRandomProducts(@Param("x") int x,@Param("category1") String category1);
    @Query(value = "SELECT * from products ORDER BY created DESC LIMIT :d",nativeQuery = true)
    List<Product> findXNewestProducts(@Param("d") int d);
    @Query(value = "SELECT * FROM Products INNER JOIN categories ON products.category_id=categories.category_id where categories.category_name=:category1 ORDER BY products.created desc LIMIT :x",nativeQuery = true)
    List<Product> findXNewestProductsByCategory(@Param("x") int x,@Param("category1") String category);
}
