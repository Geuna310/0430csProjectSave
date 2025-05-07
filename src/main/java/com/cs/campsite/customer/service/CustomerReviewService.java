package com.cs.campsite.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cs.campsite.customer.dto.BestReview;
import com.cs.campsite.customer.entity.Review;
import com.cs.campsite.customer.repository.CustomerReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerReviewService {

    private final CustomerReviewRepository reviewRepository;

    public List<BestReview> getBestReviews() {
        List<Review> reviews = reviewRepository.findTopBestReviews();

        return reviews.stream().map(r -> {
            BestReview dto = new BestReview();
            dto.setReviewNo(r.getReviewNo());
            dto.setReviewRating(r.getReviewRating());
            dto.setReviewText(r.getReviewText());

            dto.setCampsiteNo(r.getCampsite().getCampsiteNo());
            dto.setCampsiteName(r.getCampsite().getCampsiteName());
            dto.setCampsiteImageUrl(r.getCampsite().getCampsiteImageUrl());

            return dto;
        }).collect(Collectors.toList());
    }
    
    public BestReview getReviewDetail(Long reviewNum) {
        Review r = reviewRepository.findById(reviewNum)
            .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        BestReview dto = new BestReview();
        dto.setReviewNo(r.getReviewNo());
        dto.setReviewRating(r.getReviewRating());
        dto.setReviewText(r.getReviewText());

        dto.setCampsiteNo(r.getCampsite().getCampsiteNo());
        dto.setCampsiteName(r.getCampsite().getCampsiteName());
        dto.setCampsiteImageUrl(r.getCampsite().getCampsiteImageUrl());

        return dto;
    }
}
