package com.cs.campsite.admin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Storage_Locations_table")
public class StorageLocationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Storage_location_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private WarehousesEntity warehouse;

    @Column(name = "Storage_location_code", length = 20, nullable = false, unique = true)
    private String locationCode;

    @Column(name = "Storage_section", length = 10)
    private String section;

    @Column(name = "Storage_rack", length = 10)
    private String rack;

    @Column(name = "Storage_floor", length = 10)
    private String floor;

    @Column(name = "Storage_slot", length = 10)
    private String slot;

    public StorageLocationsEntity(WarehousesEntity warehouse, String locationCode, String section, String rack, String floor, String slot) {
        this.warehouse = warehouse;
        this.locationCode = locationCode;
        this.section = section;
        this.rack = rack;
        this.floor = floor;
        this.slot = slot;
    }

    public void update(String locationCode, String section, String rack, String floor, String slot) {
        this.locationCode = locationCode;
        this.section = section;
        this.rack = rack;
        this.floor = floor;
        this.slot = slot;
    }
}

