package com.example.ecomm.controllers;


import com.example.ecomm.dtos.CategoryDto;
import com.example.ecomm.dtos.ProductDto;
import com.example.ecomm.dtos.ProductPitchDto;
import com.example.ecomm.models.Category;
import com.example.ecomm.models.Product;
import com.example.ecomm.services.IProductService;
import com.example.ecomm.services.PitchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController {
    private final IProductService productService;
    private final IProductService supplierService;
    private final PitchService pitchService;

    public ProductController(@Qualifier("storageProductService") IProductService productService,
                             @Qualifier("dummyJsonProductService") IProductService supplierService,
                             PitchService pitchService) {
        this.productService = productService;
        this.supplierService = supplierService;
        this.pitchService = pitchService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) BigDecimal minPrice,
                                                           @RequestParam(required = false) BigDecimal maxPrice,
                                                           Pageable pageable) {
        List<ProductDto> products = productService.getAllProducts(minPrice, maxPrice, pageable).stream()
                .map(this::from)
                .toList();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(product), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product created = productService.createProduct(from(productDto));
        return new ResponseEntity<>(from(created), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable("id") Long id,
                                                     @RequestBody ProductDto productDto) {
        Product replaced = productService.replaceProduct(from(productDto), id);
        if (replaced == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(replaced), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        if (!productService.deleteProduct(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/products/import")
    public String importProducts(@RequestParam(required = false) BigDecimal minPrice,
                                 @RequestParam(required = false) BigDecimal maxPrice,Pageable pageable) {
        System.out.println("Importing from 3p");
        int count = 0;
        for (Product product : supplierService.getAllProducts(minPrice, maxPrice, pageable)) {
            productService.createProduct(product);
            count++;
        }
        return "Imported " + count + " products";
    }

    @GetMapping("/products/{id}/pitch")
    public ResponseEntity<ProductPitchDto> getProductPitch(@PathVariable("id") Long id) {
        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductPitchDto pitchDto = new ProductPitchDto();
        pitchDto.setProductId(product.getId());
        pitchDto.setNotification(pitchService.generatePitch(product));
        return ResponseEntity.ok(pitchDto);
    }

    private ProductDto from(Product product) {
        ProductDto.ProductDtoBuilder
                dto = ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .price(product.getPrice());

        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            dto.category(categoryDto);
        }
        return dto.build();
    }

    private Product from(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());

        if (dto.getCategory() != null) {
            Category category = new Category();
            category.setName(dto.getCategory().getName());
            category.setDescription(dto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }
}
