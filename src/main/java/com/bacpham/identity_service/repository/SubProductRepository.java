package com.bacpham.identity_service.repository;

import com.bacpham.identity_service.dto.request.SubProductCreationRequest;
import com.bacpham.identity_service.entity.Category;
import com.bacpham.identity_service.entity.SubProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface SubProductRepository extends JpaRepository<SubProduct, String> {

}
