package com.erp.Ecommeres.profile.Controller;



import com.erp.Ecommeres.profile.dto.ProfileDTO;
import com.erp.Ecommeres.profile.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // ✅ YOUR REQUESTED API
    @GetMapping("/profile/{userId}")
    public ProfileDTO getProfile(@PathVariable Long userId) {
        return profileService.getProfileByUserId(userId);
    }
    
    
}
