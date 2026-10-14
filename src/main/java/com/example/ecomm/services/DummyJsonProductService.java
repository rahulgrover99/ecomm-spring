package com.example.ecomm.services;

import com.example.ecomm.dtos.DummyJsonProductDto;
import com.example.ecomm.models.Category;
import com.example.ecomm.models.Product;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;

@Service
public class DummyJsonProductService implements IProductService{

    private final RestTemplate restTemplate;
    // Could not autowire. No beans of 'RestTemplate' type found.
    DummyJsonProductService(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
    }


    @Override
    public Product getProductById(Long id) {
        /// https://dummyjson.com/products/1
        try {
            ResponseEntity<DummyJsonProductDto> response =
                    restTemplate.getForEntity("https://dummyjson.com/products/{id}",
                            DummyJsonProductDto.class, id);

            System.out.println("Service called:" + response.getStatusCode());

            return from(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }

    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        throw new IllegalStateException("Creation denied");
    }


    private Product from(DummyJsonProductDto dummyJsonProductDto) {
        Product.ProductBuilder product = Product.builder()
                .description(dummyJsonProductDto.getDescription())
                .imageUrl(dummyJsonProductDto.getThumbnail())
                .price(dummyJsonProductDto.getPrice())
                .name(dummyJsonProductDto.getTitle());

        Category category = new Category();
        category.setName(dummyJsonProductDto.getCategory());

        product.category(category);
        return product.build();

    }
}
