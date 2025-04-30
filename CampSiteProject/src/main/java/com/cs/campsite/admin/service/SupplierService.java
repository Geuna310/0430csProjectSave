package com.cs.campsite.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cs.campsite.admin.dto.SupplierFormDTO;
import com.cs.campsite.admin.entity.SupplierEntity;
import com.cs.campsite.admin.repository.SupplierRepository;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    
    // 데이터베이스에 새로운 협력사 정보 등록
    @Transactional
    public void saveSupplier(SupplierFormDTO form) {
    	SupplierEntity newSupplier = new SupplierEntity(
            form.getSupplier_name(),
            form.getSupplier_contact_person(),
            form.getSupplier_contact_phone(),
            form.getSupplier_phone(),
            form.getSupplier_email(),
            form.getSupplier_address(),
            form.getSupplier_type(),
            form.getSupplier_status(),
            form.getSupplier_bank_account()
        );
    	
    	 supplierRepository.save(newSupplier);
    }


    // 데이터베이스에서 모든 협력사 정보 조회
    public List<SupplierEntity> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    // 협력사 고유번호로 특정 협력사 조회
    public SupplierEntity getSupplierById(Integer supplier_id) {
        return supplierRepository.findById(supplier_id).orElse(null);
    }

    // 협력사 정보 수정
    @Transactional
    public void updateSupplier(Integer supplier_id, SupplierFormDTO form) {
        SupplierEntity updateSupplier = supplierRepository.findById(supplier_id).orElse(null);
        if (updateSupplier != null) {
            updateSupplier.update(
                form.getSupplier_name(),
                form.getSupplier_contact_person(),
                form.getSupplier_contact_phone(),
                form.getSupplier_phone(),
                form.getSupplier_email(),
                form.getSupplier_address(),
                form.getSupplier_type(),
                form.getSupplier_status(),
                form.getSupplier_bank_account()
            );
        }
    }


    // 협력사 고유번호로 특정 협력사 정보 삭제
    @Transactional
    public void deleteSupplier(Integer supplier_id) {
        supplierRepository.deleteById(supplier_id);
    }
}

