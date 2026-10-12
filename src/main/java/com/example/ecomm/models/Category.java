package com.example.ecomm.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
