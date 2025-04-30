package com.cs.campsite.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.cs.campsite.admin.util.AdminJwtFilter;
import java.util.List;

@Configuration
public class AdminSecurityConfig {

    @Value("${admin.secret.key}")
    private String secretKey;

    // 정적 리소스 허용 (image, css, js 등) , 사용하지 않을 경우 이미지 파일 안 불러와짐
    @Bean(name = "StaticResourcesSecurityFilterChain")
    public SecurityFilterChain staticResourcesSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/image/**", "/css/**", "/js/**", "/favicon.ico")
                .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }

    // API 관련 보안 설정 (JWT 인증)
    @Bean(name = "ApiAdminSecurityFilterChain")
    public SecurityFilterChain apiAdminSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/admin/**")
                .csrf(csrf -> csrf.disable())
                .cors().and()
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/api/admin/login").permitAll()
                    .anyRequest().authenticated()
                )
                .addFilterBefore(new AdminJwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // 관리자 페이지 보안 설정 (세션 기반 인증)
    @Bean(name = "AdminPageSecurityFilterChain")
    public SecurityFilterChain adminPageSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/admin/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/adminlogin").permitAll()
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .loginPage("/adminlogin")
                    .defaultSuccessUrl("/admin", true)
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/adminlogin?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/adminlogin")
                    )
                )
                .build();
    }

    // CORS 설정
    @Bean(name = "AdminCorsConfigurationSource")
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}