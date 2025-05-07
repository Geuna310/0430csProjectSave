package com.cs.campsite.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.cs.campsite.admin.dto.SupplierFormDTO;
import com.cs.campsite.admin.entity.SupplierEntity;
import com.cs.campsite.admin.service.SupplierService;

@RestController
@RequestMapping("/api/admin/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    // 전체 협력사 정보 조회
    @GetMapping
    public List<SupplierEntity> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    // 협력사 등록
    @PostMapping
    public String saveSupplier(@RequestBody SupplierFormDTO form) {
        supplierService.saveSupplier(form);
        return "등록 성공";
    }

    // 단일 협력사 조회
    @GetMapping("/{supplier_id}")
    public SupplierEntity getSupplier(@PathVariable("supplier_id") int supplier_id) {
        return supplierService.getSupplierById(supplier_id);
    }

    // 협력사 수정
    @PutMapping("/{supplier_id}")
    public String updateSupplier(@PathVariable("supplier_id") int supplier_id, @RequestBody SupplierFormDTO form) {
        supplierService.updateSupplier(supplier_id, form);
        return "수정 성공";
    }

    // 협력사 삭제
    @DeleteMapping("/{supplier_id}")
    public String deleteSupplier(@PathVariable("supplier_id") int supplier_id) {
        supplierService.deleteSupplier(supplier_id);
        return "삭제 성공";
    }
}


