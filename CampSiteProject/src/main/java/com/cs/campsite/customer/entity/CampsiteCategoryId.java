package com.cs.campsite.customer.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CampsiteCategoryId implements Serializable {
	
	private Integer campsiteNo;
	private Integer categoryNo;
	
	public CampsiteCategoryId(Integer campsite, Integer category) {
		this.campsiteNo = campsite;
		this.categoryNo = category;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CampsiteCategoryId)) return false;
		CampsiteCategoryId that = (CampsiteCategoryId) o;
		return Objects.equals(campsiteNo, that.campsiteNo) &&
				Objects.equals(categoryNo, that.categoryNo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(campsiteNo, categoryNo);
	}
}
