package com.cs.campsite.customer.dto;

import lombok.Data;
import java.util.List;

// 추천 캠핑장 정보를 담는 DTO (편의시설 태그 포함)
@Data
public class CustomerCampsiteCardDTO {
    private int campsiteNo;              // 캠핑장 고유번호
    private String campsiteName;          // 캠핑장 이름
    private String campsiteImageUrl;      // 대표 이미지
    private String campsiteLocation;      // 위치
    private List<String> facilityTags;    // 편의시설 태그 리스트
}
