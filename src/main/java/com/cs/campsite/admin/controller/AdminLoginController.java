package com.cs.campsite.admin.controller;

import com.cs.campsite.admin.dto.AdminLoginRequestDto;
import com.cs.campsite.admin.dto.AdminLoginResponseDto;
import com.cs.campsite.admin.service.AdminLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/login")
@RequiredArgsConstructor
public class AdminLoginController {

    private final AdminLoginService adminLoginService;

    @PostMapping // POST 요청 처리 어노테이션
    public ResponseEntity<AdminLoginResponseDto> login(@RequestBody AdminLoginRequestDto loginRequest) {
        AdminLoginResponseDto responseDto = adminLoginService.login(loginRequest);
        return ResponseEntity.ok(responseDto); // JWT 토큰 반환
    }
}
