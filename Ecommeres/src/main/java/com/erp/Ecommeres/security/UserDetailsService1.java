package com.erp.Ecommeres.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.repo.UserRepo;



@Service
public class UserDetailsService1 implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    // 🔹 Used during LOGIN
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        return new UserPrincipal(user);
    }

    // 🔹 Used by JWT Filter
    public UserDetails loadUserById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with id: " + id)
                );

        return new UserPrincipal(user);
    }
}
