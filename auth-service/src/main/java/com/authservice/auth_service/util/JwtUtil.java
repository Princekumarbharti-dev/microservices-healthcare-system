package com.authservice.auth_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtil {
    private final SecretKey key;
    private final long exp;

    public JwtUtil(@Value("${app.jwt.secret}") String s,
                   @Value("${app.jwt.expiry-ms}") long exp) {
        this.key = Keys.hmacShaKeyFor(s.getBytes(StandardCharsets.UTF_8));
        this.exp = exp;
    }

    public String gen(String email, Map<String, Object> c) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(email)
                .claims(c)
                .issuedAt(new Date(now))
                .expiration(new Date(now + exp))
                .signWith(key)
                .compact();
    }

    public Claims parse(String t) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(t)
                .getPayload();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }
}
