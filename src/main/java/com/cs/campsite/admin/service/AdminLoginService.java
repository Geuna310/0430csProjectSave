package com.cs.campsite.admin.service;

import com.cs.campsite.admin.dto.AdminLoginRequestDto;
import com.cs.campsite.admin.dto.AdminLoginResponseDto;
import com.cs.campsite.admin.entity.AdminLoginEntity;
import com.cs.campsite.admin.repository.AdminRepository;
import com.cs.campsite.admin.util.AdminJwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLoginService {

    private final AdminRepository adminRepository;
    private final AdminJwtUtil jwtUtil;

    public AdminLoginResponseDto login(AdminLoginRequestDto dto) {
        AdminLoginEntity admin = adminRepository.findByAdminId(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다."));

        if (!admin.getAdminPassword().equals(dto.getAdminPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(admin.getAdminId());
        return new AdminLoginResponseDto(token);
    }
}
