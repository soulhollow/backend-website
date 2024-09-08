package com.example.backend;


import com.example.backend.config.JwtTokenUtil;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    private String testSecretKey = "b367df33022d750d471deda3b5ee188a2db358435875f5f0f7451e60072b625a"; // Ensure it matches the actual key in the code
    private String testUsername = "testuser";
    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        Map<String, Object> claims = new HashMap<>();
        token = Jwts.builder()
                .setClaims(claims)
                .setSubject(testUsername)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, testSecretKey)
                .compact();
    }

    @Test
    void testGenerateToken() {

        com.example.backend.model.User user = new com.example.backend.model.User();
        user.setUsername(testUsername);
        user.setPassword("password");

        String generatedToken = jwtTokenUtil.generateToken(user);

        assertNotNull(generatedToken);
    }


    @Test
    void testExtractUsername() {
        String username = jwtTokenUtil.extractUsername(token);
        assertEquals(testUsername, username);
    }

    @Test
    void testIsTokenExpired() {
        boolean isExpired = jwtTokenUtil.isTokenExpired(token);
        assertFalse(isExpired);
    }

    @Test
    void testValidateToken() {
        // Create a Spring Security UserDetails object for testing
        UserDetails userDetails = User.builder()
                .username(testUsername)
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        boolean isValid = jwtTokenUtil.validateToken(token, userDetails);
        assertTrue(isValid);
    }

}
