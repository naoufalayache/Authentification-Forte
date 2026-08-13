package com.naoufalayache.authentication.services.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.naoufalayache.DTO.LoginDTO;
import com.naoufalayache.DTO.RegisterDTO;
import com.naoufalayache.authentication.model.User;
import com.naoufalayache.authentication.repository.UserRepository;
import com.naoufalayache.authentication.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final String erreur = "Les identifiants ne sont pas bons, veuillez réessayer";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())){
            throw new IllegalArgumentException(erreur);
        }

        User user = new User();
        user.setEmail(registerDTO.getEmail());

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
            throw new IllegalArgumentException(erreur);
        }
        user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));

        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public User login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
            .orElseThrow(()->new IllegalArgumentException(erreur));
        
        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new IllegalArgumentException(erreur);
        }

        return user;
    }
    
}
