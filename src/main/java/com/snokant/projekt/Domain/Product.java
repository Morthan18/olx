package com.snokant.projekt.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @NotNull(message = "{empty}")
    private String name;
    @NotNull(message = "{empty}")
    private String description;
    @NotNull(message = "{empty}")
    private Integer price;
    @NotNull(message = "{empty}")
    private Long category_id;
    @Nullable
    private int owner_id;
    @Nullable
    private String image;
    @CreationTimestamp
    private LocalDateTime created;


}
