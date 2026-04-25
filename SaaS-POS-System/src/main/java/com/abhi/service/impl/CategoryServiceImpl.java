package com.abhi.service.impl;

import com.abhi.domain.UserRole;
import com.abhi.exceptions.UserException;
import com.abhi.mapper.CategoryMapper;
import com.abhi.model.Category;
import com.abhi.model.Store;
import com.abhi.model.User;
import com.abhi.payload.dto.CategoryDto;
import com.abhi.repository.CategoryRepository;
import com.abhi.repository.StoreRepository;
import com.abhi.service.CategoryService;
import com.abhi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final StoreRepository storeRepository;
    @Override
    public CategoryDto createCategory(CategoryDto dto) throws Exception {
        User user = userService.getCurrentUser();

        Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(
                () -> new UserException("Store not found with id")
                );

        Category category = Category.builder()
                .name(dto.getName())
                .store(store)
                .build();

        checkAuthority(user, category.getStore());

        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> getCategoriesByStore(Long storeId) {

        List<Category> categories = categoryRepository.findByStoreId(storeId);

        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());

    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto dto) throws Exception {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new Exception("Category not found with id")
        );
        User user = userService.getCurrentUser();

        checkAuthority(user, category.getStore());
        category.setName(dto.getName());
        return CategoryMapper.toDto(categoryRepository.save(category));

    }

    @Override
    public void deleteCategory(Long id) throws Exception {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new Exception("Category not found with id")
        );
        User user = userService.getCurrentUser();

        checkAuthority(user, category.getStore());

        categoryRepository.delete(category);

    }

    private void checkAuthority(User user , Store store) throws Exception {
        boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore = user.equals(store.getStoreAdmin());

        if(!(isAdmin && isSameStore) && ! isManager) {
            throw new Exception("You don't have permission to manage this category");
        }
    }
}
