package com.naoufalayache.authentication.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.naoufalayache.authentication.model.User;
import com.naoufalayache.authentication.repository.UserRepository;
import com.naoufalayache.authentication.services.UserService;
import com.naoufalayache.exception.AppError;

@Service 
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long userId){
        return userRepository.findByIdAndEnabledTrue(userId);
    }

    public List<User> getUserByEmail(String email, int page, int size){
        if (page < 0 || size < 1 || size > 100) {
            throw new AppError("La page doit être positive ou nulle et la taille entre 1 et 100");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Order.desc("createdAt"),
                        Sort.Order.desc("id")));

        return userRepository.findByEmailContainingIgnoreCase(email,pageable).getContent();
        
    }
}
