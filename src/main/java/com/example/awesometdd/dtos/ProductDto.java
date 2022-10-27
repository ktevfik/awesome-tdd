package com.example.awesometdd.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:25
 */
@Data
@Builder
public class ProductDto {

    private Integer totalPrice;

    private Integer id;
}