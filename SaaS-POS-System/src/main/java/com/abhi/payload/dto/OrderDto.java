package com.abhi.payload.dto;

import com.abhi.domain.PaymentType;
import com.abhi.model.Branch;
import com.abhi.model.Customer;
import com.abhi.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private Long branchId;
    private Long customerId;
    private BranchDto branch;
    private UserDto cashier;
    private Customer customer;
    private List<OrderItemDto> orderItems;
    private PaymentType paymentType;
}
