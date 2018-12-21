package com.snokant.projekt.Model.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@NoArgsConstructor
public class ProductDomain {
    public ProductDomain(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    private String name;
    private String description;
    private Integer price;
}
