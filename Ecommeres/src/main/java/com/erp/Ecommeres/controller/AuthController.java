package com.erp.Ecommeres.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.dto.AuthResponseDto;
import com.erp.Ecommeres.dto.LoginRequestDto;
import com.erp.Ecommeres.dto.SignupRequestDto;
import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    // -----------------------------
    // SIGNUP
    // -----------------------------
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequestDto request) {
        User user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto request) {
        AuthResponseDto response = userService.loginUser(request);
        return ResponseEntity.ok(response);
    }

   
}

