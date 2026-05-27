package com.authservice.auth_service.service;

import com.authservice.auth_service.DTO.*;
import com.authservice.auth_service.entity.AuthUser;
import com.authservice.auth_service.exception.*;
import com.authservice.auth_service.repository.AuthUsersRepository;
import com.authservice.auth_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthUsersRepository authUsersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        AuthUser authUser = authUsersRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Email does not exist"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), authUser.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", authUser.getUserId());
        claims.put("role", authUser.getRole());
        claims.put("email", authUser.getEmail());

        String token = jwtUtil.gen(authUser.getEmail(), claims);

        return new LoginResponseDTO(token, authUser.getUserId(), authUser.getEmail(), authUser.getRole());
    }

    public ValidateResponseDTO validate(String token) {
        try{
            Claims claims= jwtUtil.parse(token);
            Integer uid = claims.get("userId", Integer.class);
            String role = claims.get("role", String.class);
            String email = claims.get("email", String.class);
            return new ValidateResponseDTO(true, uid, email, role);
        }catch (Exception e){
            return new ValidateResponseDTO(false, null, null, null);
        }
    }

    public void updatePassword(String auth, UpdatePasswordRequestDTO r) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new InvalidTokenException("Missing token");
        }

        String t = auth.substring(7);
        String email = jwtUtil.extractEmail(t);

        if (email == null || email.isBlank()) {
            throw new InvalidTokenException("Invalid token");
        }

        if (r.getOldPassword() == null || r.getOldPassword().isBlank()) {
            throw new MissingFieldException("Old password is required");
        }

        if (r.getNewPassword() == null || r.getNewPassword().isBlank()) {
            throw new MissingFieldException("New password is required");
        }

        if (r.getConfirmPassword() == null || r.getConfirmPassword().isBlank()) {
            throw new MissingFieldException("Confirm password is required");
        }

        if (!r.getNewPassword().equals(r.getConfirmPassword())) {
            throw new PasswordMismatchException("New password and confirm password do not match");
        }

        AuthUser u = authUsersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(r.getOldPassword(), u.getPassword())) {
            throw new OldPasswordIncorrectException("Old password is incorrect");
        }

        u.setPassword(passwordEncoder.encode(r.getNewPassword()));
        authUsersRepository.save(u);
    }

    public void forgotPassword(ForgotPasswordRequestDTO r) {
        if (!r.getNewPassword().equals(r.getConfirmPassword())) {
            throw new PasswordMismatchException("New password and confirm password do not match");
        }

        AuthUser u = authUsersRepository.findByEmail(r.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Email does not exist"));

        u.setPassword(passwordEncoder.encode(r.getNewPassword()));
        authUsersRepository.save(u);
    }

}
