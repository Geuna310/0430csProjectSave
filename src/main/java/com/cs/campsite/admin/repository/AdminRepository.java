package com.cs.campsite.admin.repository;

import com.cs.campsite.admin.entity.AdminLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminLoginEntity, Integer> {
    Optional<AdminLoginEntity> findByAdminId(String adminId);
}
