package com.cs.campsite.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "section_types_table")
public class SectionTypes {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sectionTypeNo;
	
	@Column(nullable = false)
	private String sectionTypeName;

}