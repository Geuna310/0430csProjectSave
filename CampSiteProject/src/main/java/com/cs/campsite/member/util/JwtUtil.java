package com.cs.campsite.member.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtUtil {

    private final Key key;

    // application.properties에서 SecretKey 읽어오기( 암호화 사용했기 때문에 서버 재시작시 문제 발생 => application.properties 에 secretKey 추가함 )
    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }
    
    public String generateToken(String memberId, String role) { // 회원 역할도 포함해서 토큰 생성
        long now = System.currentTimeMillis();
        long expiry = now + 1000 * 60 * 60; // 1시간 동안 유효
        return Jwts.builder()
                .setSubject(memberId)
                .claim("role", role)  // 회원 역할을 토큰에 포함( 캠핑장 있으면 업체, 없으면 고객 )
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiry))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getMemberId(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            System.out.println("Error extracting member ID from token: " + e.getLocalizedMessage());
            return null;
        }
    }

    // 토큰에서 역할(role) 추출
    public String getRole(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role", String.class);  // 토큰에서 role 클레임을 추출
        } catch (Exception e) {
            System.out.println("Error extracting role from token: " + e.getLocalizedMessage());
            return null;
        }
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // HttpServletRequest에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}