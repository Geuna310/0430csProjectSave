package com.cs.campsite.admin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_table")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "product_name", nullable = false)
    private String product_name;

    @Column(name = "product_price", nullable = false)
    private int product_price;

    @Column(name = "product_image", length = 200)
    private String product_image;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity product_supplier_id;

    public ProductEntity(String name, int price, String image, SupplierEntity supplier) {
        this.product_name = name;
        this.product_price = price;
        this.product_image = image;
        this.product_supplier_id = supplier;
    }

    public void update(String name, int price, String image) {
        this.product_name = name;
        this.product_price = price;
        this.product_image = image;
    }
}

