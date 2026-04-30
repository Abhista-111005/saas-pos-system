package com.abhi.service.impl;

import com.abhi.domain.UserRole;
import com.abhi.mapper.UserMapper;
import com.abhi.model.Branch;
import com.abhi.model.Store;
import com.abhi.model.User;
import com.abhi.payload.dto.UserDto;
import com.abhi.repository.BranchRepository;
import com.abhi.repository.StoreRepository;
import com.abhi.repository.UserRepository;
import com.abhi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createStoreEmployee(UserDto employee, Long storeId) throws Exception {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new Exception("Store not found")
                );
        Branch branch = null;
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER) {
            if(employee.getBranchId()==null) {
                throw new Exception("Branch ID is required for Branch Manager role");
            }
            branch = branchRepository.findById(employee.getBranchId())
                    .orElseThrow(
                            () -> new Exception("Branch not found")
                    );
        }
        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        User savedEmployee = userRepository.save(user);
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER && branch !=null) {
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDto createBranchEmployee(UserDto employee, Long branchId) throws Exception {

        Branch branch = branchRepository.findById(employee.getBranchId())
                .orElseThrow(
                        () -> new Exception("Branch not found")
                );

        if(employee.getRole()==UserRole.ROLE_BRANCH_CASHIER ||
        employee.getRole()==UserRole.ROLE_BRANCH_MANAGER) {
            User user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }
         throw new Exception("Branch Role not supported ");
    }

    @Override
    public User updateEmployee(Long employeeId, User employeeDetails) {
        return null;
    }

    @Override
    public void deleteEmployee(Long employeeId) {

    }

    @Override
    public List<User> findStoreEmployee(Long storeId, UserRole role) {
        return List.of();
    }
}
