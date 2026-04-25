package com.abhi.mapper;

import com.abhi.model.Category;
import com.abhi.payload.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toDto(Category category) {

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore()!=null ? category.getStore().getId(): null)
                .build();

    }
}
