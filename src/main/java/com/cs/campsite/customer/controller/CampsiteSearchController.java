package com.cs.campsite.customer.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs.campsite.customer.dto.CampsiteSearchCondition;
import com.cs.campsite.customer.dto.CampsiteSimpleDTO;
import com.cs.campsite.customer.entity.CampsiteSortType;
import com.cs.campsite.customer.service.CampsiteSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/campsite")
@RequiredArgsConstructor
public class CampsiteSearchController {
	
	private final CampsiteSearchService camSearchService;
	
	
	/** 방 정보를 등록하지 않은 캠핑장은 아예 나오지 않습니다. */
	// TODO : 날짜 관련 검색은 예약 개수에 따라 room_quantity가 감소하는 기능을 짜지 않으면 작동 X 
	@GetMapping("/search")
	public Page<CampsiteSimpleDTO> searchCampsites(
			@RequestParam(name="name", required = false) String name,
			@RequestParam(name="capacity", required = false) Integer capacity,
			@RequestParam(name="startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(name="endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(name="categoryNames", required = false) List<String> category,
			@RequestParam(name="sectionTypeNames", required = false) List<String> sectionType,
			@RequestParam(name="facilityCategoryNames", required = false) List<String> facilityCategory,
			@RequestParam(name="minPrice", required = false) Integer minPrice,
			@RequestParam(name="maxPrice", required = false) Integer maxPrice,
			@RequestParam(name="minPeakPrice", required = false) Integer minPeakPrice,
			@RequestParam(name="maxPeakPrice", required = false) Integer maxPeakPrice,
			@RequestParam(name="sort", required = false, defaultValue = "createdAt") CampsiteSortType sort,
			@PageableDefault(size = 10, sort = "campsiteCreatedAt", 
			direction = Sort.Direction.DESC) Pageable pageable) {
		CampsiteSearchCondition csc = new CampsiteSearchCondition();
		csc.setName(name);
		csc.setMinCapacity(capacity);
		csc.setStartDate(startDate);
		csc.setEndDate(endDate);
		csc.setCategoryNames(category);
		csc.setSectionTypeNames(sectionType);
		csc.setFacilityCategoryNames(facilityCategory);
		csc.setMinPrice(minPrice);
		csc.setMaxPrice(maxPrice);
		csc.setMinPeakPrice(minPeakPrice);
		csc.setMaxPeakPrice(maxPeakPrice);
		csc.setSort(sort);
		
		return camSearchService.searchCampsites(csc, pageable);
	}
	
}