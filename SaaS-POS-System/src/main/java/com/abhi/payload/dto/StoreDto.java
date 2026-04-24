package com.abhi.payload.dto;

import com.abhi.domain.StoreStatus;
import com.abhi.model.StoreContact;
import com.abhi.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreDto {


    private Long id;


    private String brand;


    private UserDto storeAdmin;

    private LocalDateTime createdAt;

    private LocalDateTime updatesAt;

    private String description;

    private String storeType;

    private StoreStatus status;

    private StoreContact contact;

}
