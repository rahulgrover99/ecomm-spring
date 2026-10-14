package com.example.ecomm.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseModel{

    private String name;
    private String description;
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    private String imageUrl;
    @ManyToOne
    private Category category;
}
