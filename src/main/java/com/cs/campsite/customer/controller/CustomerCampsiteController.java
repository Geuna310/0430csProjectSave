package com.cs.campsite.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.campsite.customer.dto.CampsiteCategory;
import com.cs.campsite.customer.dto.CustomerCampsiteCardDTO;
import com.cs.campsite.customer.service.CustomerCampsiteService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer/campsites")
public class CustomerCampsiteController {
    private final CustomerCampsiteService customerCampsiteService;

    @GetMapping("/recommended") // 최신순
    public ResponseEntity<List<CustomerCampsiteCardDTO>> getRecommendedCampsites() {
        List<CustomerCampsiteCardDTO> list = customerCampsiteService.getRecommendedCampsites();
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/{campsiteNo}") // 상세페이지 이동 컨트롤러
    public ResponseEntity<CustomerCampsiteCardDTO> getCampsiteDetail(@PathVariable("campsiteNo") int campsiteNo) {
        CustomerCampsiteCardDTO dto = customerCampsiteService.getCampsiteDetail(campsiteNo);
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/category/all")
    public ResponseEntity<List<CampsiteCategory>> getAllCampsitesWithAnyCategory() {
        List<CampsiteCategory> list = customerCampsiteService.getCampsitesWithCategoriesOnly();
        return ResponseEntity.ok(list);
    }


}
