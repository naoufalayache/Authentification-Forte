package com.naoufalayache.authentication.services.impl;

import java.util.Optional;

import com.naoufalayache.authentication.model.User;
import com.naoufalayache.authentication.repository.UserRepository;
import com.naoufalayache.authentication.services.UserService;

public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long userId){
        return userRepository.findByIdAndEnabledTrue(userId);
    }
}
