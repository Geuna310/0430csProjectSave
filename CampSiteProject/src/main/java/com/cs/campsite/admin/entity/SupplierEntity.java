package com.cs.campsite.admin.entity;

import org.springframework.boot.context.properties.bind.ConstructorBinding;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자 (protected)
@Entity
@Table(name = "suppliers_table")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Integer supplier_id;

    @Column(name = "supplier_name", length = 255)
    private String supplier_name;

    @Column(name = "supplier_contact_person", length = 20)
    private String supplier_contact_person;

    @Column(name = "supplier_contact_phone", length = 13)
    private String supplier_contact_phone;

    @Column(name = "supplier_phone", length = 13)
    private String supplier_phone;

    @Column(name = "supplier_email", length = 30)
    private String supplier_email;

    @Column(name = "supplier_address", length = 255)
    private String supplier_address;

    @Column(name = "supplier_type", length = 50)
    private String supplier_type;

    @Column(name = "supplier_status", length = 20, columnDefinition = "VARCHAR(20) DEFAULT '활성'")
    private String supplier_status;

    @Column(name = "supplier_bank_account", length = 50)
    private String supplier_bank_account;

    @Column(name = "supplier_created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private java.time.LocalDateTime supplier_created_at;

    @Column(name = "supplier_updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private java.time.LocalDateTime supplier_updated_at;

    // JPA 엔티티에서 최초 삽입 시 호출되는 메서드
    @PrePersist
    public void setSupplierCreatedAt() {
        this.supplier_created_at = java.time.LocalDateTime.now();  // 최초 생성 시 현재 시간으로 설정
        this.supplier_updated_at = java.time.LocalDateTime.now();
    }

    @PreUpdate
    public void setSupplierUpdatedAt() {
        this.supplier_updated_at = java.time.LocalDateTime.now();  // 업데이트 시 현재 시간으로 갱신
    }

    // 등록용 생성자
    @ConstructorBinding
    public SupplierEntity(String supplier_name, String supplier_contact_person, String supplier_contact_phone, 
    					  String supplier_phone, String supplier_email, String supplier_address, 
    					  String supplier_type, String supplier_status, String supplier_bank_account) {
        this.supplier_name = supplier_name;
        this.supplier_contact_person = supplier_contact_person;
        this.supplier_contact_phone = supplier_contact_phone;
        this.supplier_phone = supplier_phone;
        this.supplier_email = supplier_email;
        this.supplier_address = supplier_address;
        this.supplier_type = supplier_type;
        this.supplier_status = supplier_status;
        this.supplier_bank_account = supplier_bank_account;
    }

    // 수정용 메서드
    public void update(String supplier_name, 
            String supplier_contact_person,
            String supplier_contact_phone,
            String supplier_phone, 
            String supplier_email,
            String supplier_address,
            String supplier_type,
            String supplier_status,
            String supplier_bank_account) {
        this.supplier_name = supplier_name;
        this.supplier_contact_person = supplier_contact_person;
        this.supplier_contact_phone = supplier_contact_phone;
        this.supplier_phone = supplier_phone;
        this.supplier_email = supplier_email;
        this.supplier_address = supplier_address;
        this.supplier_type = supplier_type;
        this.supplier_status = supplier_status;
        this.supplier_bank_account = supplier_bank_account;
    }
}
