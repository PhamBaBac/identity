package com.bacpham.identity_service.service;

import com.bacpham.identity_service.dto.request.ProductCreationRequest;
import com.bacpham.identity_service.dto.request.SubProductCreationRequest;
import com.bacpham.identity_service.dto.response.PageResponse;
import com.bacpham.identity_service.dto.response.ProductResponse;
import com.bacpham.identity_service.dto.response.SubProductResponse;
import com.bacpham.identity_service.entity.Category;
import com.bacpham.identity_service.entity.Product;
import com.bacpham.identity_service.entity.SubProduct;
import com.bacpham.identity_service.exception.AppException;
import com.bacpham.identity_service.exception.ErrorCode;
import com.bacpham.identity_service.mapper.ProductMapper;
import com.bacpham.identity_service.mapper.SubProductMapper;
import com.bacpham.identity_service.repository.CategoryRepository;
import com.bacpham.identity_service.repository.ProductRepository;
import com.bacpham.identity_service.repository.SubProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;
    public ProductResponse createProduct(ProductCreationRequest request) {
        Product product = productMapper.toProduct(request);

        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategories()));
            log.info("Categories: {}", categories);
            product.setCategories(categories);
        }
        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public PageResponse<ProductResponse> getProductPage(int page, int pageSize, String title) {
        Sort sort = Sort.by("createdAt").descending();

        if (title != null && !title.isEmpty()) {
            page = 1;
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        Page<Product> pageData;

        if (title != null && !title.isEmpty()) {
            pageData = productRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else {
            pageData = productRepository.findAll(pageable);
        }

        return PageResponse.<ProductResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(productMapper::toProductResponse).toList())
                .build();
    }

    //delete product by id
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    //get product by id
    public ProductResponse getProductById(String id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public ProductResponse updateProduct(String id, ProductCreationRequest request) {
        log.info("Updating product id: {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        productMapper.updateProduct(product, request);

        if (request.getCategories() != null && !request.getCategories().isEmpty()) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategories()));
            product.setCategories(categories);
        }

        product = productRepository.save(product);

        return productMapper.toProductResponse(product);
    }
}