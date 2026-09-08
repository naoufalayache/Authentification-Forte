package com.naoufalayache.authentication.services;

import java.util.Optional;

import com.naoufalayache.authentication.model.User;

public interface UserService {
    public Optional<User> findById(Long userId);
}
