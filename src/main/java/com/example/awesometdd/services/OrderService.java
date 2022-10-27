package com.example.awesometdd.services;

import com.example.awesometdd.clients.PaymentClient;
import com.example.awesometdd.repositories.OrderRepository;
import com.example.awesometdd.dtos.OrderDto;
import com.example.awesometdd.models.Order;
import com.example.awesometdd.services.requests.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:22
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;

    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {
        BigDecimal totalPrice = createOrderRequest.getUnitPrice().multiply(BigDecimal.valueOf(createOrderRequest.getAmount()));
        Order order = Order.builder().id(32).totalPrice(totalPrice).build();
        this.paymentClient.pay(order);
        Order save = orderRepository.save(order);
        return OrderDto.builder().id(save.getId()).totalPrice(totalPrice).build();
    }
}
