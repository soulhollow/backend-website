package com.example.backend.repository;
import com.example.backend.dto.OrderProductDTO;
import com.example.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT new com.example.backend.dto.OrderProductDTO(oi.order.id, p.name, oi.quantity, oi.price, o.orderStatus) " +
            "FROM OrderItem oi " +
            "JOIN oi.product p " +
            "JOIN oi.order o " +
            "JOIN o.user u " +
            "WHERE u.username = :username")
    List<OrderProductDTO> findOrderProductsByUsername(String username);
}
