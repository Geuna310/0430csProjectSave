package com.cs.campsite.member.entity;

import java.time.LocalTime;
import java.util.List;

import com.cs.campsite.customer.entity.CampsiteFacility;
import com.cs.campsite.customer.entity.ViewCategory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "campsites_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campsite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer campsiteNo;

    private String campsiteName;

    private String campsiteLocation;

    @Column(columnDefinition = "TEXT")
    private String campsiteDescription;

    private String campsiteCreatedAt;

    private String campsitesBusinessNumber;
    
    private String campsiteMapimage;
    
    private LocalTime checkIn;
    
    private LocalTime checkOut;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;
    
    private String campsiteImageUrl;

    
    @OneToMany(mappedBy = "campsite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampsiteFacility> facilities;
    
    @ManyToMany
    @JoinTable(
        name = "campsite_categories",
        joinColumns = @JoinColumn(name = "campsite_no"),
        inverseJoinColumns = @JoinColumn(name = "category_no")
    )
    private List<ViewCategory> categories;
    
}

