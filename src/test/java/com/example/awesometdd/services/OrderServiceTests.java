package com.example.awesometdd.services;

import com.example.awesometdd.dtos.OrderDto;
import com.example.awesometdd.services.requests.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:20
 */
public class OrderServiceTests {

    private OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        orderService = new OrderService();
    }

    public static Stream<Arguments> order_requests() {
        return Stream.of(
                Arguments.of("code1", 5, BigDecimal.valueOf(12.3), BigDecimal.valueOf(61.5)),
                Arguments.of("code2", 10, BigDecimal.valueOf(15), BigDecimal.valueOf(150))
        );
    }

    @ParameterizedTest
    @MethodSource("order_requests")
    public void it_should_create_orders(String productCode, Integer amount, BigDecimal unitPrice, BigDecimal totalPrice) {
        // given
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder()
                .productCode(productCode)
                        .amount(amount)
                                .unitPrice(unitPrice)
                                        .build();

        // when
        OrderDto order = orderService.createOrder(createOrderRequest);

        // then
        then(order.getTotalPrice()).isEqualTo(totalPrice);
    }

    @Test
    public void it_should_fail_order_creation_when_payment_failed() {

    }
}
