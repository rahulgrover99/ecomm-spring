package com.example.ecomm.services;

import com.example.ecomm.models.Category;
import com.example.ecomm.models.Product;
import com.example.ecomm.repository.CategoryRepository;
import com.example.ecomm.repository.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public StorageProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {

//        productRepository.deleteAll();
//        productRepository.save(new Product());
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product input) {
        input.setId(null);
        input.setCategory(findOrCreateCategory(input.getCategory()));
        return productRepository.save(input);
    }

    private Category findOrCreateCategory(Category category) {
        Optional<Category> optionalCategory = categoryRepository.findByNameMatches(category.getName());
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }

        category.setId(null);
        category = categoryRepository.save(category);
        return category;
    }
}
