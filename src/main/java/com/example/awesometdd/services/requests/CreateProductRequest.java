package com.example.awesometdd.services.requests;

import lombok.*;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 00:28
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String productCode;
    private Integer amount;
    private Integer unitPrice;
}
