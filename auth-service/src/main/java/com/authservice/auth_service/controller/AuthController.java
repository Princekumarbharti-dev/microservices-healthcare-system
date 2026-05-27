package com.authservice.auth_service.controller;

import com.authservice.auth_service.DTO.*;
import com.authservice.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponse = authService.login(loginRequestDTO);
        return ResponseEntity.ok(loginResponse);
    }
    @GetMapping("/validate")
    public ResponseEntity<ValidateResponseDTO> validateRequest(@RequestHeader("Authorization") String auth){
        String token=auth;

        if(token.startsWith("Bearer ")){
            //fetch token
            token=token.substring(7);
        }

        ValidateResponseDTO validateResponseDTO=authService.validate(token);

        if(!validateResponseDTO.valid) return ResponseEntity.status(401).body(validateResponseDTO);

        return ResponseEntity.ok(validateResponseDTO);
    }

    @PutMapping("/password")
    public ResponseEntity<Map<String, String>> updatePassword(
            @RequestHeader(value = "Authorization", required = true) String auth,
            @Valid @RequestBody UpdatePasswordRequestDTO r) {

        authService.updatePassword(auth, r);

        return ResponseEntity.ok(Map.of("message", "Password updated"));
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequestDTO r) {

        authService.forgotPassword(r);
        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }










}
