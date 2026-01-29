package com.erp.Ecommeres.config;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.erp.Ecommeres.security.TokenAuthenticationFilter;
import com.erp.Ecommeres.security.UserDetailsService1;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService1 userDetailsService;

    public SecurityConfig(UserDetailsService1 userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // ---------- PASSWORD ENCODER ----------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ---------- AUTH PROVIDER ----------
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ---------- AUTH MANAGER ----------
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(
                org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    // ---------- JWT FILTER ----------
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    // ---------- SECURITY FILTER CHAIN ----------
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(
                    (req, res, e) ->
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                )
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // ✅ ALLOW PREFLIGHT REQUESTS (VERY IMPORTANT)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ PUBLIC AUTH APIs
                .requestMatchers("/auth/**").permitAll()
                
                .requestMatchers("/uploads/**").permitAll()


             // ✅ PUBLIC PRODUCT VIEWING (HOME PAGE)
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()

                // ✅ ADMIN ONLY PRODUCT MANAGEMENT
                .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                // ✅ ADMIN DASHBOARD APIs
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // ✅ EVERYTHING ELSE NEEDS AUTH
                .anyRequest().authenticated()
            );

        http.addFilterBefore(
                tokenAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
