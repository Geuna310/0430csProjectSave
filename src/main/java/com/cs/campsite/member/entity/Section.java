package com.cs.campsite.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "sections_table")
public class Section {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sectionNo;
	
	@Column(nullable = false)
	private String sectionName;
	
	private String sectionDescription;
	
	private String sectionMap;
	
	@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "campsite_no")
	private Campsite campsite;

}
