package com.cs.campsite.customer.dto;

public record CampsiteSimpleDTO(
	int campsiteNo,
	String campsiteName,
	String campsiteLocation,
	String campsiteImgUrl
) {}