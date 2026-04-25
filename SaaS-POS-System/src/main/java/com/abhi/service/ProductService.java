package com.abhi.service;

import com.abhi.model.User;
import com.abhi.payload.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto, User user) throws Exception;
    ProductDto updateProduct(Long id, ProductDto productDto, User user) throws Exception;
    void deleteProduct(Long id, User user) throws Exception;
    List<ProductDto> getProductByStoreId(Long StoreId);

    List<ProductDto> searchByKeyword(Long storeId, String keyword);
}
