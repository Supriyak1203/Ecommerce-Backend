package com.erp.Ecommeres.profile.Controller;



import com.erp.Ecommeres.profile.dto.ProfileDTO;
import com.erp.Ecommeres.profile.entity.Profile;
import com.erp.Ecommeres.profile.service.ProfileService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

 // CREATE or UPDATE profile
    @PostMapping("/save")
    public ResponseEntity<?> saveProfile(@RequestBody Profile profile) {
        return ResponseEntity.ok(profileService.saveOrUpdateProfile(profile));
    }

    // ✅ YOUR REQUESTED API
    @GetMapping("/profile/{userId}")
    public ProfileDTO getProfile(@PathVariable Long userId) {
        return profileService.getProfileByUserId(userId);
    }
    
    
}
