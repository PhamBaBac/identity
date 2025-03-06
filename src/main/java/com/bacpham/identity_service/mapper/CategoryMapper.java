package com.bacpham.identity_service.mapper;

import com.bacpham.identity_service.dto.request.CategoryRequest;
import com.bacpham.identity_service.dto.response.CategoryResponse;
import com.bacpham.identity_service.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.Set;
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);
}

