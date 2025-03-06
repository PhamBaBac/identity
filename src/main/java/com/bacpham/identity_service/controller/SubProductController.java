package com.bacpham.identity_service.controller;

import com.bacpham.identity_service.dto.request.ApiResponse;
import com.bacpham.identity_service.dto.request.ProductCreationRequest;
import com.bacpham.identity_service.dto.request.SubProductCreationRequest;
import com.bacpham.identity_service.dto.response.ProductResponse;
import com.bacpham.identity_service.dto.response.SubProductResponse;
import com.bacpham.identity_service.service.SubProductService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-products")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class SubProductController {
    SubProductService subProductService;

    @PostMapping("/create")
    ApiResponse<SubProductResponse> createProduct(@RequestBody @Validated SubProductCreationRequest request) {
        log.info("Creating product: {}", request);
        return ApiResponse.<SubProductResponse>builder()
                .result(subProductService.createSubProduct(request))
                .build();
    }

    @GetMapping("")
    ApiResponse<List<SubProductResponse>> getProducts() {
        return ApiResponse.<List<SubProductResponse>>builder()
                .result(subProductService.getSubProducts())
                .build();
    }

    @DeleteMapping("/remove-sub-product/{id}")
    ApiResponse<Void> deleteProduct(@PathVariable String id) {
        subProductService.delete(id);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/update")
    ApiResponse<SubProductResponse> updateProduct(@RequestBody @Validated SubProductCreationRequest request) {
        return ApiResponse.<SubProductResponse>builder()
                .result(subProductService.updateSubProduct(request))
                .build();
    }

}
