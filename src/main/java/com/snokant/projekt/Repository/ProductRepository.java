package com.snokant.projekt.Repository;

import com.snokant.projekt.Model.Product;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.OrderBy;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByCategoryId(Long category);
    List<Product> findProductsByOwnerIdOrderByCreatedDesc(int id);
    @Query(value = "SELECT * from products where name like concat('%',:name,'%')",nativeQuery = true)
    List<Product> findProductsByName(@Param("name")String name);

    //Long countByCategoryId(Long categoryId);

    Long countByCategoryId(Long category);
    @Query(value = "SELECT * FROM Products where categoryId=:category1 and id NOT LIKE :actual ORDER BY RAND() LIMIT :howMany", nativeQuery = true)
    List<Product> findXRandomProducts(@Param("howMany") int howMany, @Param("category1") Long category1, @Param("actual") int actual);

    @Query(value = "SELECT * from products ORDER BY created DESC LIMIT :d", nativeQuery = true)
    List<Product> findXNewestProducts(@Param("d") int d);


    @Query(value = "SELECT * from Products where categoryId=:cat limit :lim", nativeQuery = true)
    List<Product> findXNewestProductsByCategory(@Param("lim") int lim, @Param("cat") int category);


    @Query(value = "SELECT name from products where name like concat('%',:phrase,'%') group by name", nativeQuery = true)
    List<String> findProductNamesByContaining(@Param("phrase") String phrase);

    @Query("select count(p.id) from Product p")
    Integer findProductCount();
}
