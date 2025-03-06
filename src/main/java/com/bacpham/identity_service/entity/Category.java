package com.bacpham.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
    public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        String id;

        @Column(nullable = false, unique = true, length = 255)
        String title;

        String slug;

        @Column(columnDefinition = "TEXT")
        String description;

        @Column(name = "parent_id")
        String parentId;
}
