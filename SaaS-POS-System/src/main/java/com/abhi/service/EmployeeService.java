package com.abhi.service;

import com.abhi.domain.UserRole;
import com.abhi.model.User;
import com.abhi.payload.dto.UserDto;

import java.util.List;

public interface EmployeeService {

    UserDto createStoreEmployee(UserDto employee , Long storeId) throws Exception;
    UserDto createBranchEmployee(UserDto employee , Long branchId) throws Exception;
    User updateEmployee(Long employeeId ,UserDto employeeDetails);
    void deleteEmployee(Long employeeId);
    List<UserDto> findStoreEmployee(Long storeId , UserRole role);
    List<UserDto> findBranchEmployee(Long branchId , UserRole role);




}
