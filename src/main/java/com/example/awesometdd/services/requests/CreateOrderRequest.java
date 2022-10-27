package com.example.awesometdd.services.requests;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:28
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private String productCode;
    private Integer amount;
    private BigDecimal unitPrice;
}
