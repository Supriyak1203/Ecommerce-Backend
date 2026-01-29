package com.erp.Ecommeres.profile.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erp.Ecommeres.profile.entity.Profile;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);

}
