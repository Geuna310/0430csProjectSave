package com.cs.campsite.admin.entity;

import java.beans.ConstructorProperties;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Warehouses_table")
public class WarehousesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Integer warehouse_id;

    @Column(name = "warehouse_name", length = 100)
    private String warehouse_name;

    @Column(name = "warehouse_location", length = 255)
    private String warehouse_location;

    @Column(name = "warehouse_phone", length = 13)
    private String warehouse_phone;

    @Column(name = "warehouse_email", length = 255)
    private String warehouse_email;
    
    @Column(name = "warehouse_created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private java.time.LocalDateTime warehouse_created_at;

    @Column(name = "warehouse_updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private java.time.LocalDateTime warehouse_updated_at;
    
    // JPA 엔티티에서 최초 삽입 시 호출되는 메서드
    @PrePersist
    public void setSupplierCreatedAt() {
        this.warehouse_created_at = java.time.LocalDateTime.now();  // 최초 생성 시 현재 시간으로 설정
        this.warehouse_updated_at = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void setSupplierUpdatedAt() {
        this.warehouse_updated_at = java.time.LocalDateTime.now();  // 업데이트 시 현재 시간으로 갱신
    }
    
    
    @ConstructorProperties({"warehouses_name", "warehouses_location", "warehouses_phone", "warehouses_email"})
    public WarehousesEntity(String warehouse_name, String warehouses_location, String warehouses_phone, String warehouses_email) {
        this.warehouse_name = warehouse_name;
        this.warehouse_location = warehouses_location;
        this.warehouse_phone = warehouses_phone;
        this.warehouse_email = warehouses_email;
    }

    public void update(String warehouse_name, String warehouses_location, String warehouses_phone, String warehouses_email) {
        this.warehouse_name = warehouse_name;
        this.warehouse_location = warehouses_location;
        this.warehouse_phone = warehouses_phone;
        this.warehouse_email = warehouses_email;
    }

}
