package com.cs.campsite.customer.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CampsiteSectionTypesId implements Serializable {
	private Integer campsiteNo;
	private Integer sectionNo;
	private Integer sectionTypeNo;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CampsiteSectionTypesId)) return false;
		CampsiteSectionTypesId that = (CampsiteSectionTypesId) o;
		return Objects.equals(campsiteNo, that.campsiteNo) &&
				Objects.equals(sectionNo, that.sectionNo) &&
				Objects.equals(sectionTypeNo, that. sectionTypeNo);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(campsiteNo, sectionNo,  sectionTypeNo);
	}
}