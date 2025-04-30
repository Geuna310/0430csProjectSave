package com.cs.campsite.member.util;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.repository.MemberRepository;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

       
    	/** JwtUtil에서 호출한 메소드  */
        String token = jwtUtil.resolveToken(request);

        if (token != null) {

            try {
                if (jwtUtil.validateToken(token)) {
                    String memberId = jwtUtil.getMemberId(token);
                    Member member = memberRepository.findByMemberId(memberId)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
                    
                    UserDetailsImpl userDetails = new UserDetailsImpl(member);
                    
                    
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                            		userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                }
            } catch (JwtException | IllegalArgumentException e) {
                // 토큰 유효하지 않을 경우 무시
            
            }
        } 
        
        filterChain.doFilter(request, response);
    }
}