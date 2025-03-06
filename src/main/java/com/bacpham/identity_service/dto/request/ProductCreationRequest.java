package com.bacpham.identity_service.dto.request;

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
public class ProductCreationRequest {
    String title;
    String slug;
    String description;
    String content;
    String supplier;
    Set<String> categories;
    Set<SubProductCreationRequest> subProducts;
}

