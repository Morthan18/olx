package com.snokant.projekt.Model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    public Product(String name, Long id) {
        this.name = name;
        this.id = id;
    }


    public Product(String name, Long id, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String name, Long id, String image) {
        this.name = name;
        this.id = id;
        this.image = image;
    }

    public Product(Long id, String name, String description, Integer price, Long categoryId, User owner, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.owner = owner;
        this.image = image;
    }

    public Product(String name, String description,Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Long categoryId;
    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;
    @Nullable
    private String image;
    @CreationTimestamp
    @Nullable
    private LocalDateTime created;

}
