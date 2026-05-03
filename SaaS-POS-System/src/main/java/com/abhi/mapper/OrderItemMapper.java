package com.abhi.mapper;

import com.abhi.model.OrderItem;
import com.abhi.payload.dto.OrderItemDto;

public class OrderItemMapper {

    public static OrderItemDto toDto(OrderItem item) {

        if(item == null) {
            return null;
        }
        return OrderItemDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .product(ProductMapper.toDto(item.getProduct()))
                .build();

    }

}
