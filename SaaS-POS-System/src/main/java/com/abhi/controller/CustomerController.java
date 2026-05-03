package com.abhi.controller;

import com.abhi.model.Customer;
import com.abhi.payload.response.ApiResponse;
import com.abhi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping()
    private ResponseEntity<Customer> create (@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }
    @PostMapping("/{id}")
    private ResponseEntity<Customer> update (
            @PathVariable Long id, @RequestBody Customer customer) throws Exception {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("{id}")
    private ResponseEntity<ApiResponse> delete (@PathVariable Long id) throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Customer deleted successfully");
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    private ResponseEntity<List<Customer>> getAll () throws Exception {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    @GetMapping("/search")
    private ResponseEntity<List<Customer>> search (
            @RequestParam String q
    ) throws Exception {
        return ResponseEntity.ok(customerService.searchCustomers(q));
    }
}
