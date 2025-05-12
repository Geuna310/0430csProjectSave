package com.cs.campsite.customer.dto;

import java.time.LocalDate;
import java.util.List;

import com.cs.campsite.customer.entity.CampsiteSortType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampsiteSearchCondition {
	
	private String name;
	private Integer minCapacity;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<String> categoryNames;
	private List<String> sectionTypeNames;
	private List<String> facilityCategoryNames;
	private Integer minPrice;
	private Integer maxPrice;
	private Integer minPeakPrice;
	private Integer maxPeakPrice;
	private CampsiteSortType sort;

}