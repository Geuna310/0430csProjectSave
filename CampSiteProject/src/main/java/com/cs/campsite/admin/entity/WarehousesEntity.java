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
    private Integer warehouses_id;

    @Column(name = "warehouse_name", length = 100)
    private String warehouses_name;

    @Column(name = "warehouses_location", length = 255)
    private String warehouses_location;

    @Column(name = "warehouses_phone", length = 13)
    private String warehouses_phone;

    @Column(name = "warehouses_email", length = 255)
    private String warehouses_email;
    
    @ConstructorProperties({"warehouses_name", "warehouses_location", "warehouses_phone", "warehouses_email"})
    public WarehousesEntity(String warehouse_name, String warehouses_location, String warehouses_phone, String warehouses_email) {
        this.warehouses_name = warehouse_name;
        this.warehouses_location = warehouses_location;
        this.warehouses_phone = warehouses_phone;
        this.warehouses_email = warehouses_email;
    }

    public void update(String warehouse_name, String warehouses_location, String warehouses_phone, String warehouses_email) {
        this.warehouses_name = warehouse_name;
        this.warehouses_location = warehouses_location;
        this.warehouses_phone = warehouses_phone;
        this.warehouses_email = warehouses_email;
    }

}
