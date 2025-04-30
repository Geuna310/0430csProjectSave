package com.cs.campsite.customer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.campsite.customer.dto.CampsiteCategory;
import com.cs.campsite.customer.dto.CustomerCampsiteCardDTO;
import com.cs.campsite.customer.entity.CampsiteFacility;
import com.cs.campsite.customer.repository.CustomerCampsiteRepository;
import com.cs.campsite.member.entity.Campsite;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerCampsiteService {

    private final CustomerCampsiteRepository repository;

    // ✅ 1. 추천 캠핑장 목록 반환 (편의시설 태그 포함)
    public List<CustomerCampsiteCardDTO> getRecommendedCampsites() {
        List<Campsite> campsites = repository.findRecommendedCampsitesWithFacilities();

        return campsites.stream().map(c -> {
            CustomerCampsiteCardDTO dto = new CustomerCampsiteCardDTO();
            dto.setCampsiteNo(c.getCampsiteNo());
            dto.setCampsiteName(c.getCampsiteName());
            dto.setCampsiteImageUrl(c.getCampsiteImageUrl());
            dto.setCampsiteLocation(c.getCampsiteLocation());

            List<String> facilityNames = c.getFacilities().stream()
                .map(CampsiteFacility::getFacility)
                .map(f -> f.getFacilityName())
                .distinct()
                .collect(Collectors.toList());

            dto.setFacilityTags(facilityNames);
            return dto;
        }).collect(Collectors.toList());
    }

    // ✅ 2. 상세 조회 (편의시설 포함)
    public CustomerCampsiteCardDTO getCampsiteDetail(int campsiteNum) {
        Campsite campsite = repository.findCampsiteWithFacilities(campsiteNum)
            .orElseThrow(() -> new RuntimeException("캠핑장 없음"));

        CustomerCampsiteCardDTO dto = new CustomerCampsiteCardDTO();
        dto.setCampsiteNo(campsite.getCampsiteNo());
        dto.setCampsiteName(campsite.getCampsiteName());
        dto.setCampsiteLocation(campsite.getCampsiteLocation());
        dto.setCampsiteImageUrl(campsite.getCampsiteImageUrl());

        List<String> facilityNames = campsite.getFacilities().stream()
            .map(cf -> cf.getFacility().getFacilityName())
            .distinct()
            .collect(Collectors.toList());
        dto.setFacilityTags(facilityNames);

        return dto;
    }

    // ✅ 3. 이색 캠핑장 목록 (편의시설 태그 제거!)
    @Transactional(readOnly = true) // ✅ 핵심! 여기에 꼭 붙여야 함
    public List<CampsiteCategory> getCampsitesWithCategoriesOnly() {
        List<Campsite> campsites = repository.findAllWithCategory();

        return campsites.stream().map(c -> {
            CampsiteCategory dto = new CampsiteCategory();
            dto.setCampsiteNo(c.getCampsiteNo());
            dto.setCampsiteName(c.getCampsiteName());
            dto.setCampsiteImageUrl(c.getCampsiteImageUrl());
            dto.setCampsiteLocation(c.getCampsiteLocation());

            // ✅ categoryTags 세팅
            List<String> categoryNames = c.getCategories().stream()
                .map(category -> category.getCategoryName())
                .distinct()
                .collect(Collectors.toList());

            dto.setCategoryTags(categoryNames);
            return dto;
        }).collect(Collectors.toList());
    }
}
