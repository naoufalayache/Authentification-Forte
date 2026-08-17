package com.naoufalayache.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.naoufalayache.DTO.LoginDTO;
import com.naoufalayache.DTO.RegisterDTO;
import com.naoufalayache.authentication.model.User;
import com.naoufalayache.authentication.services.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(
        @RequestBody RegisterDTO registerDTO
    ) {
        return authService.register(registerDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String login(
        @RequestBody LoginDTO loginDTO
    ) {
        return authService.login(loginDTO);
    }
    
}
