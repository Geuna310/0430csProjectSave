package com.cs.campsite.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String role;
    // 로그인할 때 업체인지 고객인지 확인할 수 있도록 토큰과 권한 모두 받아오게함
}