package com.example.awesometdd.services;

import com.example.awesometdd.clients.PaymentClient;
import com.example.awesometdd.models.Product;
import com.example.awesometdd.repositories.ProductRepository;
import com.example.awesometdd.dtos.ProductDto;
import com.example.awesometdd.services.requests.CreateProductRequest;
import org.springframework.stereotype.Service;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:22
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PaymentClient paymentClient;

    public ProductService(ProductRepository productRepository, PaymentClient paymentClient) {
        this.productRepository = productRepository;
        this.paymentClient = paymentClient;
    }

    public ProductDto createProduct(CreateProductRequest createProductRequest) {
        Integer totalPrice = createProductRequest.getUnitPrice() * createProductRequest.getAmount();
        Product product = Product.builder()
                .unitPrice(createProductRequest.getUnitPrice())
                .amount(createProductRequest.getAmount())
                .totalPrice(totalPrice)
                .build();
        this.paymentClient.pay(product);
        Product save = productRepository.save(product);
        return ProductDto.builder()
                .id(save.getId())
                .unitPrice(createProductRequest.getUnitPrice())
                .amount(createProductRequest.getAmount())
                .totalPrice(totalPrice)
                .build();
    }
}
