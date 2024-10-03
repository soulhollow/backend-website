package com.example.backend.service;

import com.example.backend.dto.OrderProductDTO;
import com.example.backend.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    public List<OrderProductDTO> getOrderProductsByUsername(String username) {
        return orderProductRepository.findOrderProductsByUsername(username);
    }
}
