package com.cs.campsite.customer.entity;


import com.cs.campsite.member.entity.Campsite;
import com.cs.campsite.member.entity.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "campsite_categories")
@IdClass(CampsiteCategoryId.class)
public class CampsiteCategories {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "campsite_no")
	private Campsite campsiteNo;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "category_no")
	private Category categoryNo;
	
}