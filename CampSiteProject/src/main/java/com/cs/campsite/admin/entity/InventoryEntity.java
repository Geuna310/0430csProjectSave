package com.cs.campsite.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Inventory_table")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer inventory_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity inventory_product_id;

    @ManyToOne
    @JoinColumn(name = "inventory_storage_location_id")
    private WarehousesEntity inventory_storage_location_id;

    @Column(name = "Inventory_quantity")
    private int inventory_quantity;

    public InventoryEntity(ProductEntity product, WarehousesEntity warehouse, int quantity) {
        this.inventory_product_id = product;
        this.inventory_storage_location_id = warehouse;
        this.inventory_quantity = quantity;
    }

    public void update(int quantity) {
        this.inventory_quantity = quantity;
    }
}