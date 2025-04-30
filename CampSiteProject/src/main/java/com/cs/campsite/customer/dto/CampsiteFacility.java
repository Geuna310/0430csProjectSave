package com.cs.campsite.customer.dto;

import lombok.Data;
import java.util.List;

// 추천 캠핑장용 DTO – 편의시설 태그만 포함
@Data
public class CampsiteFacility {
    private int campsiteNo;               // 캠핑장 번호
    private String campsiteName;           // 이름
    private String campsiteImageUrl;       // 이미지
    private String campsiteLocation;       // 위치
    private List<String> facilityTags;     // 편의시설 태그
}
