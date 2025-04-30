package com.cs.campsite.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "admin_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminLoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_no")
    private Integer adminNo;

    @Column(name = "admin_id", nullable = false, unique = true, length = 20)
    private String adminId;

    @Column(name = "admin_password", nullable = false, length = 255)
    private String adminPassword;

    @Column(name = "admin_name", nullable = false, length = 5)
    private String adminName;

    @Column(name = "admin_phone", nullable = false, unique = true, length = 13)
    private String adminPhone;

    @Column(name = "admin_email", nullable = false, unique = true, length = 35)
    private String adminEmail;

    @Column(name = "admin_birth", nullable = false, length = 10)
    private String adminBirth;
    
    @Column(name = "admin_role", nullable = false)
    private String adminRole;

    @Column(name = "admin_created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date adminCreatedAt;

    @PrePersist
    protected void onCreate() {
        this.adminCreatedAt = new Date();
    }
    
}
