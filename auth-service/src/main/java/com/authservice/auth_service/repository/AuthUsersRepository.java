package com.authservice.auth_service.repository;

import com.authservice.auth_service.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUsersRepository extends JpaRepository<AuthUser,Integer>{
    Optional<AuthUser> findByEmail(String email);
}
