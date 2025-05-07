package com.cs.campsite.customer.entity;

import com.cs.campsite.member.entity.Campsite;
import com.cs.campsite.member.entity.Section;
import com.cs.campsite.member.entity.SectionTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "campsite_section_types")
@IdClass(CampsiteSectionTypesId.class)
public class CampsiteSectionTypes {
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campsite_no")
	private Campsite campsiteNo;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "section_no")
	private Section sectionNo;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "section_type_no")
	private SectionTypes sectionTypeNo;
	
}