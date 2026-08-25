package com.naoufalayache.authentication.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.naoufalayache.DTO.LoginDTO;
import com.naoufalayache.DTO.RegisterDTO;
import com.naoufalayache.authentication.model.User;
import com.naoufalayache.authentication.repository.UserRepository;
import com.naoufalayache.authentication.services.JwtService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    public RegisterDTO generateRegisterUser(Boolean isUserGood){
        return isUserGood ? new RegisterDTO("user@gmail.com","password","password") 
                            : new RegisterDTO("user@gmail.com","passwords","password");
    }

    public LoginDTO generateLoginUser(Boolean isUserGood){
        return isUserGood ? new LoginDTO("user@gmail.com","password","password")
                            : new LoginDTO("user@gmail.com","password","passw0rd");
    }

    @Test
    public void login_good_credentials(){
        LoginDTO userDTO = this.generateLoginUser(true);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getConfirmPassword()));
        user.setEnabled(true);

        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("user@gmail.com")).thenReturn("fakeToken");

        String result = authService.login(userDTO);

        assertEquals("fakeToken", result);

        verify(jwtService).generateToken("user@gmail.com");
    }

    @Test
    public void login_but_no_email_founded(){
        LoginDTO userDTO = this.generateLoginUser(true);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getConfirmPassword()));
        user.setEnabled(true);

        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.empty());

        assertThrows(
            IllegalArgumentException.class,
            () -> authService.login(userDTO)
        );
    }

    @Test
    public void login_but_password_missmatch(){
        LoginDTO userDTO = this.generateLoginUser(false);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getConfirmPassword()));
        user.setEnabled(true);

        when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));


        assertThrows(
            IllegalArgumentException.class,
            () -> authService.login(userDTO)
        );
    }

    @Test
    public void register_good_credentials() {
        RegisterDTO user = this.generateRegisterUser(true);

        when(userRepository.existsByEmail("user@gmail.com")).thenReturn(false);

        when(jwtService.generateToken("user@gmail.com")).thenReturn("fakeToken");

        String result = authService.register(user);

        assertEquals("fakeToken", result);

        verify(userRepository).existsByEmail("user@gmail.com");
        verify(jwtService).generateToken("user@gmail.com");
    }

    @Test
    public void register_but_password_missmatch() {
        RegisterDTO user = this.generateRegisterUser(false);

        when(userRepository.existsByEmail("user@gmail.com")).thenReturn(false);

        assertThrows(
            IllegalArgumentException.class,
            () -> authService.register(user)
        );
    }

    @Test
    public void register_but_user_already_exist() {
        RegisterDTO user = this.generateRegisterUser(false);

        when(userRepository.existsByEmail("user@gmail.com")).thenReturn(true);

        assertThrows(
            IllegalArgumentException.class,
            () -> authService.register(user)
        );
    }
    
}
