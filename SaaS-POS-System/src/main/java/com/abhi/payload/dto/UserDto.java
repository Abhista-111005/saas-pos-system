package com.abhi.payload.dto;

import com.abhi.domain.UserRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {


    private String id;


    private String fullName;


    private String email;

    private String phone;


    private UserRole role;



    private String password;

    private Long branchId;
    private Long storeId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

}
