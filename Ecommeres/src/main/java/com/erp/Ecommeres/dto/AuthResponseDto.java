package com.erp.Ecommeres.dto;

public class AuthResponseDto {

    private Long userId;
    private String fullName;
    private String email;
    private String role;    // ✅ ROLE
    private String token;   // ✅ JWT
    private String message; // ✅ MESSAGE

    // ✅ CORRECT CONSTRUCTOR ORDER
    public AuthResponseDto(Long userId,
                           String fullName,
                           String email,
                           String role,
                           String token,
                           String message) {

        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.token = token;
        this.message = message;
    }

    // ===== Getters & Setters =====

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
