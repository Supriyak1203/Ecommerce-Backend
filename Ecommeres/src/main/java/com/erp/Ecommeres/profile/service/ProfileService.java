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

        Profile profile = profileRepo.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Profile not found for userId=" + userId));

        ProfileDTO dto = new ProfileDTO();

        dto.setUserId(profile.getUserId());
        dto.setImageUrl(profile.getImageUrl());
        dto.setAge(profile.getAge());
        dto.setGender(profile.getGender());

        return dto;
    }

    public Profile saveOrUpdateProfile(Profile profileRequest) {

        return profileRepo.findByUserId(profileRequest.getUserId())
                .map(existingProfile -> {
                    existingProfile.setAge(profileRequest.getAge());
                    existingProfile.setGender(profileRequest.getGender());
                    existingProfile.setImageUrl(profileRequest.getImageUrl());
                    return profileRepo.save(existingProfile);
                })
                .orElseGet(() -> profileRepo.save(profileRequest));
    }
}
