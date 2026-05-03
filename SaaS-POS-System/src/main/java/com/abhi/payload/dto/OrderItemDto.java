package com.abhi.payload.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {

    private Long id;

    private Integer quantity;

    private Double price;

    private Long productId;


    private ProductDto product;

    private Long orderId;

}
