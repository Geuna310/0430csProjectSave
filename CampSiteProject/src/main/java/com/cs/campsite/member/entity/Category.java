package com.cs.campsite.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "category_table")
public class Category {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryNo;
	
	@Column(nullable = false)
	private String categoryName;

}