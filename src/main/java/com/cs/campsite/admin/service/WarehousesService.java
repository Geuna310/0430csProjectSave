package com.cs.campsite.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.campsite.admin.dto.WarehousesFormDTO;
import com.cs.campsite.admin.entity.WarehousesEntity;
import com.cs.campsite.admin.repository.WarehousesRepository;

@Service
public class WarehousesService {
    private final WarehousesRepository warehousesRepository;
    
    public WarehousesService(WarehousesRepository warehousesRepository) {
        this.warehousesRepository = warehousesRepository;
    }
    
    // 데이터베이스에 새로운 협력사 정보 등록
    @Transactional
    public void saveWarehouses(WarehousesFormDTO form) {
    	WarehousesEntity newWarehouse = new WarehousesEntity(
            form.getWarehouse_name(),
            form.getWarehouse_location(),
            form.getWarehouse_phone(),
            form.getWarehouse_email()
        );
    	
    	warehousesRepository.save(newWarehouse);
    }


    // 데이터베이스에서 모든 협력사 정보 조회
    public List<WarehousesEntity> getAllWarehouses() {
        return warehousesRepository.findAll();
    }

    // 협력사 고유번호로 특정 협력사 조회
    public WarehousesEntity getWarehousesById(Integer Warehouse_id) {
        return warehousesRepository.findById(Warehouse_id).orElse(null);
    }

    // 협력사 정보 수정
    @Transactional
    public void updateWarehouses(Integer Warehouse_id, WarehousesFormDTO form) {
        WarehousesEntity updateWarehouses = warehousesRepository.findById(Warehouse_id).orElse(null);
        if (updateWarehouses != null) {
            updateWarehouses.update(
                    form.getWarehouse_name(),
                    form.getWarehouse_location(),
                    form.getWarehouse_phone(),
                    form.getWarehouse_email()
            );
        }
    }


    // 협력사 고유번호로 특정 협력사 정보 삭제
    @Transactional
    public void deleteWarehouses(Integer Warehouse_id) {
        warehousesRepository.deleteById(Warehouse_id);
    }
}

