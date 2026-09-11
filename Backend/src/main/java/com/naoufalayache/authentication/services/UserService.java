package com.naoufalayache.authentication.services;

import java.util.List;
import java.util.Optional;

import com.naoufalayache.authentication.model.User;

public interface UserService {
    public Optional<User> findById(Long userId);
    public List<User> getUserByEmail(String email, int page, int size);
}
