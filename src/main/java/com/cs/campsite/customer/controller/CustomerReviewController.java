package com.cs.campsite.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.campsite.customer.dto.BestReview;
import com.cs.campsite.customer.service.CustomerReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer/reviews")
public class CustomerReviewController {

    private final CustomerReviewService reviewService;

    @GetMapping("/best")
    public List<BestReview> getBestReviews() {
        return reviewService.getBestReviews();
    }
    
    @GetMapping("/{reviewNo}")
    public ResponseEntity<BestReview> getReviewDetail(@PathVariable("reviewNo") Long reviewNo) {
        BestReview dto = reviewService.getReviewDetail(reviewNo);
        return ResponseEntity.ok(dto);
    }
}
