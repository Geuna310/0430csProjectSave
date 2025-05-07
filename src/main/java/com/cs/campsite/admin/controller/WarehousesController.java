package com.cs.campsite.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.cs.campsite.admin.dto.WarehousesFormDTO;
import com.cs.campsite.admin.entity.WarehousesEntity;
import com.cs.campsite.admin.service.WarehousesService;

@RestController
@RequestMapping("/api/admin/warehouse")
public class WarehousesController {

    private final WarehousesService warehousesService;

    public WarehousesController(WarehousesService warehousesService) {
        this.warehousesService = warehousesService;
    }

    // 전체 협력사 정보 조회
    @GetMapping
    public List<WarehousesEntity> getAllwarehouses() {
        return warehousesService.getAllWarehouses();
    }

    // 협력사 등록
    @PostMapping
    public String saveWarehouses(@RequestBody WarehousesFormDTO form) {
    	warehousesService.saveWarehouses(form);
        return "등록 성공";
    }

    // 단일 협력사 조회
    @GetMapping("/{warehouse_id}")
    public WarehousesEntity getWarehouses(@PathVariable("warehouse_id") int warehouse_id) {
        return warehousesService.getWarehousesById(warehouse_id);
    }

    // 협력사 수정
    @PutMapping("/{warehouse_id}")
    public String updateWarehouses(@PathVariable("warehouse_id") int warehouse_id, @RequestBody WarehousesFormDTO form) {
    	warehousesService.updateWarehouses(warehouse_id, form);
        return "수정 성공";
    }

    // 협력사 삭제
    @DeleteMapping("/{warehouse_id}")
    public String deleteWarehouses(@PathVariable("warehouse_id") int warehouse_id) {
    	warehousesService.deleteWarehouses(warehouse_id);
        return "삭제 성공";
    }
}


