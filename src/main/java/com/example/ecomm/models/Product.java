package com.example.ecomm.models;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Category category;
}
