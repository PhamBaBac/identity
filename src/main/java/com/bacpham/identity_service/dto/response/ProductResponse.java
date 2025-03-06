package com.bacpham.identity_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductResponse {
    String id;
    String title;
    String slug;
    String description;
    String content;
    String supplier;
    boolean isDeleted;
    Set<CategoryResponse> categories;
    Set<SubProductResponse> subProducts;
}
