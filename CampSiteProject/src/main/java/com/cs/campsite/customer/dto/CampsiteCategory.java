package com.cs.campsite.customer.dto;

import lombok.Data;
import java.util.List;

// 이색 캠핑장용 DTO – 카테고리 태그만 포함
@Data
public class CampsiteCategory {
    private int campsiteNo;               // 캠핑장 번호
    private String campsiteName;           // 이름
    private String campsiteImageUrl;       // 이미지
    private String campsiteLocation;       // 위치
    private List<String> categoryTags;     // 카테고리 태그
}
