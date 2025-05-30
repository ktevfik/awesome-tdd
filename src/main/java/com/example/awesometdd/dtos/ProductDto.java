package com.example.awesometdd.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;
    private Integer unitPrice;
    private Integer amount;
    private Integer totalPrice;
}