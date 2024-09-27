package com.example.backend.service;


import com.example.backend.config.JwtTokenUtil;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.UserDTO;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Zur Verschlüsselung der Passwörter

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public User registerUser(UserDTO userDTO) {
        // Benutzer mit verschlüsseltem Passwort registrieren
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User getUserByUsername(String username) {
        // Hole den Benutzer basierend auf dem Benutzernamen
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Überprüfen, ob der Benutzer existiert, andernfalls null zurückgeben oder Exception werfen
        // Du könntest hier auch eine benutzerdefinierte Exception werfen, falls gewünscht
        return userOptional.orElse(null);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public String authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // Generate and return the JWT token
                return jwtTokenUtil.generateToken(user);
            }
        }
        return null; // Invalid credentials
    }

}
