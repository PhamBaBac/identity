package com.bacpham.identity_service.service;

import com.bacpham.identity_service.dto.request.CategoryRequest;
import com.bacpham.identity_service.dto.response.CategoryResponse;
import com.bacpham.identity_service.dto.response.UserResponse;
import com.bacpham.identity_service.entity.Category;
import com.bacpham.identity_service.mapper.CategoryMapper;
import com.bacpham.identity_service.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("Creating category: {}", request.getParentId());
        Category category = categoryMapper.toCategory(request);

        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    //Delete category

    public void deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }


}
