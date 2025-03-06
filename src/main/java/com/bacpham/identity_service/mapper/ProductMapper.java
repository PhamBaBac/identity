package com.bacpham.identity_service.mapper;

import com.bacpham.identity_service.dto.request.ProductCreationRequest;
import com.bacpham.identity_service.dto.response.ProductResponse;
import com.bacpham.identity_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "subProducts", ignore = true)
    Product toProduct(ProductCreationRequest request);

    ProductResponse toProductResponse(Product product);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "subProducts", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductCreationRequest request);
}