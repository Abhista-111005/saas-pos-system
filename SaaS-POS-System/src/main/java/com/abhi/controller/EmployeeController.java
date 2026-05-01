package com.abhi.controller;

import com.abhi.domain.UserRole;
import com.abhi.model.User;
import com.abhi.payload.dto.UserDto;
import com.abhi.payload.response.ApiResponse;
import com.abhi.service.EmployeeService;
import com.abhi.service.impl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDto> createStoreEmployee(
            @RequestBody UserDto userDto, @PathVariable Long storeId) throws Exception {

        UserDto employee = employeeService.createStoreEmployee(userDto,storeId);

        return ResponseEntity.ok(employee);

    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDto> createBranchEmployee(
            @RequestBody UserDto userDto, @PathVariable Long branchId) throws Exception {

        UserDto employee = employeeService.createBranchEmployee(userDto,branchId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(
            @PathVariable Long id,@RequestBody UserDto userDto) throws Exception {

        User employee = employeeService.updateEmployee(id,userDto);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(
            @PathVariable Long id) throws Exception {
        employeeService.deleteEmployee(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Employee deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<List<UserDto>> storeEmployee(
            @PathVariable Long id, @RequestParam(required = false)UserRole userRole) throws Exception {

        List<UserDto> employee = employeeService.findStoreEmployee(id,userRole);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<List<UserDto>> branchEmployee(
            @PathVariable Long id, @RequestParam(required = false)UserRole userRole) throws Exception {

        List<UserDto> employee = employeeService.findBranchEmployee(id,userRole);
        return ResponseEntity.ok(employee);
    }


}
