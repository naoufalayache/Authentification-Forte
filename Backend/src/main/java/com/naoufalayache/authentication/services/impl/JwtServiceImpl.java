package com.naoufalayache.authentication.services.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.naoufalayache.authentication.services.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder jwtEncoder;

    @Value("${spring.application.name}")
    private String application;

    @Value("${jwt.token.expiresIn}")
    private long expiresIn;

    @Override
    public String generateToken(String email) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(application)
            .subject(email)
            .issuedAt(now)
            .expiresAt(now.plus(expiresIn,ChronoUnit.MINUTES))
            .build();
        
        return jwtEncoder
            .encode(JwtEncoderParameters.from(claims))
            .getTokenValue();
    }
    
}
