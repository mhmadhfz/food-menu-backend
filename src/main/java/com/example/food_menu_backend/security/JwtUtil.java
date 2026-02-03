package com.example.food_menu_backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;

    private final long jwtExpiration;

    public JwtUtil(
                @Value("${jwt.secret}") String secret,
                @Value("${jwt.expiration}") long jwtExpiration
        ) {
            byte[] keyBytes = Base64.getDecoder().decode(secret);
            this.key = Keys.hmacShaKeyFor(keyBytes);
            this.jwtExpiration = jwtExpiration;
        }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
