package com.cs.campsite.admin.repository;

import com.cs.campsite.admin.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    // 필요한 추가 쿼리 메서드 작성 가능
}

