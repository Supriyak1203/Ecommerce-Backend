package com.erp.Ecommeres.profile.service;

import org.springframework.stereotype.Service;

import com.erp.Ecommeres.profile.dto.ProfileDTO;
import com.erp.Ecommeres.profile.entity.Profile;
import com.erp.Ecommeres.profile.repo.ProfileRepo;

@Service
public class ProfileService {

    private final ProfileRepo profileRepo;

    public ProfileService(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    public ProfileDTO getProfileByUserId(Long userId) {

        Profile profile = profileRepo.findByUserId(userId);

        ProfileDTO dto = new ProfileDTO();
        dto.setUserId(profile.getUser().getId());
        dto.setFullName(profile.getUser().getFullName());
        dto.setEmail(profile.getUser().getEmail());
        dto.setImageUrl(profile.getImageUrl());
        dto.setAge(profile.getAge());
        dto.setGender(profile.getGender());

        return dto;
    }
}
