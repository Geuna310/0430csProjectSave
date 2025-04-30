package com.cs.campsite.customer.dto;

import lombok.Data;

@Data
public class BestReview {
    private Long reviewNo;
    private int reviewRating;
    private String reviewText;

    private int campsiteNo;
    private String campsiteName;
    private String campsiteImageUrl;
}
