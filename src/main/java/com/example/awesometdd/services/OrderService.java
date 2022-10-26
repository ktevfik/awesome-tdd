package com.example.awesometdd.services;

import com.example.awesometdd.dtos.OrderDto;
import com.example.awesometdd.services.requests.CreateOrderRequest;

import java.math.BigDecimal;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:22
 */
public class OrderService {
    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        BigDecimal totalPrice = createOrderRequest.getUnitPrice().multiply(BigDecimal.valueOf(createOrderRequest.getAmount()));
        return OrderDto.builder().totalPrice(totalPrice).build();
    }
}
