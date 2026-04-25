package com.abhi.payload.dto;
import com.abhi.model.Category;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDto {


    private Long id;


    private String name;


    private String sku;

    private String description;

    private Double mrp;

    private Double sellingPrice;
    private String brand;
    private String image;

    private CategoryDto category;

    private Long categoryId;
    private Long storeId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
