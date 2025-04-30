package com.cs.campsite.admin.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AdminJwtUtil {

    @Value("${admin.secret.key}")
    private String secretKey; // application.properties에서 읽어온 비밀 키

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    // JWT 생성
    public String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes())) // application.properties에서 읽어온 비밀 키 사용
                .compact();
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())) // application.properties에서 읽어온 비밀 키 사용
                    .build()
                    .parseClaimsJws(token); // JWT 검증
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT에서 사용자 정보 추출
    public String extractSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())) // application.properties에서 읽어온 비밀 키 사용
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
