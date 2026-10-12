package com.example.ecomm.controllers;


import com.example.ecomm.dtos.CategoryDto;
import com.example.ecomm.dtos.ProductDto;
import com.example.ecomm.dtos.ProductPitchDto;
import com.example.ecomm.models.Product;
import com.example.ecomm.services.IProductService;
import com.example.ecomm.services.PitchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final IProductService iProductService;
    private final PitchService pitchService;


    public ProductController(IProductService iProductService, PitchService pitchService) {
        this.iProductService = iProductService;
        this.pitchService = pitchService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        Product product = iProductService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(product), HttpStatus.OK);

    }

    @GetMapping("/products/{id}/pitch")
    public ResponseEntity<ProductPitchDto> getProductPitch(@PathVariable("id") Long id) {
        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product product = iProductService.getProductById(id);
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
            dto.category(categoryDto);
        }
        return dto.build();
    }
}
