package com.example.ecomm.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double Price;
    private String imageUrl;
    private Category category;
}
