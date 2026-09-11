package com.naoufalayache.authentication.controller;

import org.springframework.web.bind.annotation.RestController;

import com.naoufalayache.authentication.model.User;
import com.naoufalayache.authentication.services.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController 
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUserByEmail(
        @RequestParam("email") String email,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ){
        return userService.getUserByEmail(email, page, size);
    }
}
