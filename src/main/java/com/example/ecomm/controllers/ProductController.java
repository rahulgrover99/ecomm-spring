package com.example.ecomm.controllers;


import com.example.ecomm.dtos.CategoryDto;
import com.example.ecomm.dtos.ProductDto;
import com.example.ecomm.models.Product;
import com.example.ecomm.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class ProductController {
    private final IProductService iProductService;


    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        Product product = iProductService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(product), HttpStatus.OK);

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
            dto.category(categoryDto);
        }
        return dto.build();
    }
}
