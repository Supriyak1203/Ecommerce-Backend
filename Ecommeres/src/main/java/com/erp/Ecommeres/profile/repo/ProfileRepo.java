package com.erp.Ecommeres.profile.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.Ecommeres.profile.entity.Profile;

public interface ProfileRepo extends JpaRepository<Profile, Long> {

    Profile findByUserId(Long userId);
}
