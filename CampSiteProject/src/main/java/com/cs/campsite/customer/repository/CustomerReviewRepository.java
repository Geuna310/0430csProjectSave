package com.cs.campsite.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cs.campsite.customer.entity.Review;

public interface CustomerReviewRepository extends CrudRepository<Review, Long> {

    @Query("""
        SELECT r FROM Review r
        JOIN FETCH r.campsite c
        ORDER BY r.reviewRating DESC, r.reviewCreatedAt DESC
    """)
    List<Review> findTopBestReviews(); // 필요 시 LIMIT 처리
}
