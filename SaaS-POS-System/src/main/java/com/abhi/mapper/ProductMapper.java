package com.abhi.mapper;

import com.abhi.model.Product;
import com.abhi.model.Store;
import com.abhi.payload.dto.ProductDto;

public class ProductMapper {

    public static ProductDto toDto(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .mrp(product.getMrp())
                .sellingPrice(product.getSellingPrice())
                .brand(product.getBrand())
                .storeId(product.getStore() !=null ?product.getStore().getId():null)
        //        .categoryId(product.getCategory().getId())
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();


    }
    public static  Product toEntity(ProductDto productDto, Store store) {
        return Product.builder()
                .name(productDto.getName())
                .sku(productDto.getSku())
                .description(productDto.getDescription())
                .mrp(productDto.getMrp())
                .sellingPrice(productDto.getSellingPrice())
                .brand(productDto.getBrand())
                .build();

    }

}
