package com.abhi.service;

import com.abhi.domain.OrderStatus;
import com.abhi.domain.PaymentType;
import com.abhi.payload.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto) throws Exception;
    OrderDto getOrderById(Long orderId) throws Exception;
    List<OrderDto> getOrderByBranch(Long branchId,
                                    Long customerId,Long cashierId,
                                    PaymentType paymentType,
                                    OrderStatus status) throws Exception;

    List<OrderDto> getOrderByCashier(Long cashierId);
    void deleteOrder(Long id) throws Exception;
    List<OrderDto> getTodayOrdersByBranch(Long branchId) throws Exception;
    List<OrderDto> getOrdersByCustomerId(Long customerId) throws Exception;
    List<OrderDto> getTop5ResearchOrdersByBranchId(Long branchId) throws Exception;



}
