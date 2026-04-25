package com.abhi.controller;

import com.abhi.payload.dto.CategoryDto;
import com.abhi.payload.response.ApiResponse;
import com.abhi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) throws Exception {

        return ResponseEntity.ok(
                categoryService.createCategory(categoryDto)
        );
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity <List<CategoryDto>> getCategoryByStoreId(
            @PathVariable Long StoreId) throws Exception {

        return ResponseEntity.ok(
                categoryService.getCategoriesByStore(StoreId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity <CategoryDto> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) throws Exception {
        return ResponseEntity.ok(
                categoryService.updateCategory(id, categoryDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <ApiResponse> deleteCategory(
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) throws Exception {
        categoryService.updateCategory(id,categoryDto);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setMessage("Category deleted successfully");

        return ResponseEntity.ok(
                apiResponse
        );
    }
}
