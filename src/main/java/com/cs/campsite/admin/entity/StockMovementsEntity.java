package com.cs.campsite.admin.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Stock_Movements_table")
public class StockMovementsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_movement_id")
    private Integer stock_movement_id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity stock_movement_product_id;

    @ManyToOne
    @JoinColumn(name = "Storage_location_id", nullable = false)
    private StorageLocationsEntity stock_movement_storage_location_id;

    @Column(name = "stock_movements_quantity")
    private int stock_movements_quantity;

    @Column(name = "stock_movement_type")
    private String stock_movement_type;

    @Column(name = "stock_movement_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime stock_movement_date;

    public StockMovementsEntity(ProductEntity product, StorageLocationsEntity location, int quantity, String type) {
        this.stock_movement_product_id = product;
        this.stock_movement_storage_location_id = location;
        this.stock_movements_quantity = quantity;
        this.stock_movement_type = type;
    }

    @PrePersist
    public void prePersist() {
        if (this.stock_movement_date == null) {
            this.stock_movement_date = LocalDateTime.now();
        }
    }
}
