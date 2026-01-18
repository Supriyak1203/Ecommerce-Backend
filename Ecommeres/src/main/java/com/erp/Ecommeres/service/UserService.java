package com.erp.Ecommeres.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erp.Ecommeres.dto.AuthResponseDto;
import com.erp.Ecommeres.dto.LoginRequestDto;
import com.erp.Ecommeres.dto.SignupRequestDto;
import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.repo.UserRepo;
import com.erp.Ecommeres.security.TokenProvider;
import com.erp.Ecommeres.security.UserPrincipal;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    // ---------------------------------
    // REGISTER USER (USER ONLY)
    // ---------------------------------
    public User registerUser(SignupRequestDto request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Mobile number already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ✅ ALWAYS USER (NO ADMIN SIGNUP)
        user.setRole("USER");

        return userRepository.save(user);
    }

    // ---------------------------------
    // LOGIN USER / ADMIN
    // ---------------------------------
    public AuthResponseDto loginUser(LoginRequestDto request) {

        try {
            // 1️⃣ Authenticate credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // 2️⃣ Load user from DB
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() ->
                            new RuntimeException("User not found with email: " + request.getEmail())
                    );

            // 3️⃣ Create UserPrincipal
            UserPrincipal principal = new UserPrincipal(user);

            // 4️⃣ Generate JWT
            String token = tokenProvider.generateToken(principal);

            // 5️⃣ Return response with ROLE
            return new AuthResponseDto(
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole(), // USER / ADMIN
                    token,
                    "Login successful"
            );

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
