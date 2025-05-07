package com.cs.campsite.customer.entity;

import java.util.List;

import com.cs.campsite.member.entity.Campsite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category_table")
@Getter
@Setter
public class ViewCategory {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer categoryNo;

 private String categoryName;

 @ManyToMany(mappedBy = "categories")
 private List<Campsite> campsites;
}
