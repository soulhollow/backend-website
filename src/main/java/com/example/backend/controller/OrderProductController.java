package com.example.backend.controller;

import com.example.backend.config.JwtTokenUtil;
import com.example.backend.dto.OrderProductDTO;
import com.example.backend.service.OrderProductService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/orderproducts")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PermitAll
    @GetMapping("/user")
    public List<OrderProductDTO> getOrderProductsByUsername(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println("Authorization Header: " + authorizationHeader);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
        String token = authorizationHeader.substring(7); // Entfernt das "Bearer " Präfix
        String username = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println("Username: " + username);
        return orderProductService.getOrderProductsByUsername(username);
    }
}
