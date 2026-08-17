package com.naoufalayache.authentication.services;

public interface JwtService {
    String generateToken(String email);
}
