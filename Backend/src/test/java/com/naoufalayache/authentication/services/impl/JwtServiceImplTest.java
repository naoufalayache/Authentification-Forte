package com.naoufalayache.authentication.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class JwtServiceImplTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private JwtServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(
            jwtService,
            "application",
            "authentication"
        );

        ReflectionTestUtils.setField(
            jwtService,
            "expiresIn",
            15L
        );
    }

    @Test
    public void generateToken(){
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        when(jwt.getTokenValue()).thenReturn("fakeToken");

        String result = jwtService.generateToken("test");

        assertNotNull(result);
        assertEquals("fakeToken", result);
    }
    
}
