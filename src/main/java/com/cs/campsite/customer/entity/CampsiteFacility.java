package com.cs.campsite.customer.entity;

import com.cs.campsite.member.entity.Campsite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "campsite_facilities")
public class CampsiteFacility {
    @EmbeddedId
    private CampsiteFacilityId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("campsiteNo")
    @JoinColumn(name = "campsite_no")
    private Campsite campsite;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("facilityNo")
    @JoinColumn(name = "facility_no")
    private Facility facility;
}
