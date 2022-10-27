package com.example.awesometdd.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:25
 */
@Data
@Builder
public class OrderDto {

    private BigDecimal totalPrice;

    private Integer id;
}