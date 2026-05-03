package com.abhi.controller;

import com.abhi.domain.OrderStatus;
import com.abhi.domain.PaymentType;
import com.abhi.payload.dto.OrderDto;
import com.abhi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody OrderDto order) throws Exception {
        return ResponseEntity.ok(orderService.createOrder(order));

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<OrderDto>> getOrderByBranch(@PathVariable Long branchId,
                                                          @RequestParam(required = false) Long customerId,
                                                          @RequestParam(required = false) Long cashierId,
                                                          @RequestParam(required = false) PaymentType paymentType,
                                                          @RequestParam(required = false) OrderStatus orderStatus)
            throws Exception {
        return ResponseEntity.ok(orderService.getOrderByBranch(branchId,
                customerId,cashierId, paymentType, orderStatus));
    }
    @GetMapping("/cashier/{cashierId}")
    public ResponseEntity<List<OrderDto>> getOrderByCashier(@PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(orderService.getOrderByCashier(id));
    }
    @GetMapping("/today/branch/{id}")
    public ResponseEntity<List<OrderDto>> getTodayOrder(@PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(orderService.getTodayOrdersByBranch(id));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderDto>> getCustomersOrders(@PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(id));
    }

    @GetMapping("/recent/{branchId}")
    public ResponseEntity<List<OrderDto>> getRecentOrder(@PathVariable Long branchId
    ) throws Exception {
        return ResponseEntity.ok(orderService.getTop5ResearchOrdersByBranchId(branchId));
    }



}
