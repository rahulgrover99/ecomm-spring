package com.example.ecomm.services;

import com.example.ecomm.models.Product;

public interface IProductService {
    Product getProductById(Long id);
}
