package com.snokant.projekt.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
    private String name;
    private String description;
    private Integer price;
    private Long category_id;
    @Nullable
    private String image;
    public Product(Long product_id, String name) {
        this.product_id = product_id;
        this.name = name;
    }

}
