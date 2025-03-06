package com.bacpham.identity_service.mapper;

import com.bacpham.identity_service.dto.request.SubProductCreationRequest;
import com.bacpham.identity_service.dto.response.SubProductResponse;
import com.bacpham.identity_service.entity.SubProduct;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubProductMapper {

    SubProduct toSubProduct(SubProductCreationRequest request);

    SubProductResponse toSubProductResponse(SubProduct subProduct);

    void updateSubProduct(@MappingTarget SubProduct subProduct, SubProductCreationRequest request);
}