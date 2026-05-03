package com.abhi.mapper;

import com.abhi.model.Order;
import com.abhi.payload.dto.OrderDto;

import java.util.stream.Collectors;

public class OrderMapper {

     public static OrderDto toDto(Order order) {
         return OrderDto.builder()
                 .id(order.getId())
                 .totalAmount(order.getTotalAmount())
                 .branchId(order.getBranch().getId())
                 .cashier(UserMapper.toDTO(order.getCashier()))
                 .customer(order.getCustomer())
                 .paymentType(order.getPaymentType())
                 .createdAt(order.getCreatedAt())
                 .orderItems(order.getOrderItems().stream().map(OrderItemMapper::toDto)
                         .collect(Collectors.toList()))
                 .build();



     }

}
