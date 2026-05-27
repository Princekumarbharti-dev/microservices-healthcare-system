package com.user_profile.User_ProfileService.repository;

import com.user_profile.User_ProfileService.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {

    boolean existsByEmail(String email);

    Optional<UserProfile> findByEmail(String email);
}
