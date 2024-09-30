package com.example.backend;


import com.example.backend.dto.LoginRequest;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.config.JwtTokenUtil;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Transactional
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("encoded_password");
    }

    @Test
    void testAuthenticateUser_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword())).thenReturn(true);
        when(jwtTokenUtil.generateToken(testUser)).thenReturn("test_jwt_token");

        String token = userService.authenticateUser(loginRequest);

        assertEquals("test_jwt_token", token);
    }

    @Test
    void testAuthenticateUser_InvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("wrongpassword");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(loginRequest.getPassword(), testUser.getPassword())).thenReturn(false);

        String token = userService.authenticateUser(loginRequest);

        assertEquals(null, token);
    }

    @Test
    void testAuthenticateUser_UserNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("unknownuser");
        loginRequest.setPassword("password");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        String token = userService.authenticateUser(loginRequest);

        assertEquals(null, token);
    }
}
