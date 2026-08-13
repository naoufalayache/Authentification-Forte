package com.naoufalayache.authentication.services;

import com.naoufalayache.DTO.LoginDTO;
import com.naoufalayache.DTO.RegisterDTO;
import com.naoufalayache.authentication.model.User;

public interface AuthService {
    User register(RegisterDTO registerDTO);
    User login(LoginDTO loginDTO);
}
