package com.erp.Ecommeres.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erp.Ecommeres.entity.User;

import jakarta.transaction.Transactional;

public interface UserRepo extends JpaRepository<User, Long> {

    // ---------- AUTH / LOGIN ----------
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

    // ---------- GENERAL SEARCH ----------
    List<User> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String fullName,
            String email
    );

    // ---------- CRM (CUSTOMERS ONLY) ----------
    List<User> findByRole(String role);

    List<User> findByRoleAndFullNameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCase(
            String role1, String fullName,
            String role2, String email
    );

    // ---------- PASSWORD UPDATE ----------
    @Modifying
    @Transactional
    @Query("""
        UPDATE User u
        SET u.password = :password
        WHERE u.email = :email
    """)
    void updatePassword(@Param("email") String email,
                        @Param("password") String password);
    
    long countByRole(String role);

}
