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
@Table(indexes = @Index(name = "idx_col_category", columnList = "category_id"))
public class Product extends BaseModel{

    private String name;
    private String description;
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
