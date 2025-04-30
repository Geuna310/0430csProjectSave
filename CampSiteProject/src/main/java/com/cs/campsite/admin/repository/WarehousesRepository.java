package com.cs.campsite.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs.campsite.admin.entity.WarehousesEntity;

@Repository
public interface WarehousesRepository extends JpaRepository<WarehousesEntity, Integer> {

}
