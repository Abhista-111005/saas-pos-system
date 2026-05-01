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
import java.util.stream.Collectors;

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
    public User updateEmployee(Long employeeId, UserDto employeeDetails) {

        User exsistingEmployee = userRepository.findById(employeeId)
                .orElseThrow(
                        () -> new RuntimeException("Employee not exsisting with given id")
                );

        Branch branch = branchRepository.findById(employeeDetails.getBranchId())
                .orElseThrow(
                        () -> new RuntimeException("Branch not found")
                );
        exsistingEmployee.setEmail(employeeDetails.getEmail());
        exsistingEmployee.setFullName(employeeDetails.getFullName());
        exsistingEmployee.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));
        exsistingEmployee.setRole(employeeDetails.getRole());
        exsistingEmployee.setBranch(branch);

        return userRepository.save(exsistingEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        User employee = userRepository.findById(employeeId)
                .orElseThrow(
                        () -> new RuntimeException("Employee not existing with given id")
                );
        userRepository.delete(employee);

    }

    @Override
    public List<UserDto> findStoreEmployee(Long storeId, UserRole role) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(
                        () -> new RuntimeException("Store not found")
                );
        return userRepository.findByStore(store)
                .stream()
                .filter(user -> role==null || user.getRole()==role)
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findBranchEmployee(Long branchId, UserRole role) {

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(
                        () -> new RuntimeException("Branch not found")
                );

        return userRepository.findByBranchId(branchId)
                .stream()
                .filter(user -> role==null || user.getRole()==role)
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());

    }

}
