package com.roseny.logisticscrm.repositories;

import com.roseny.logisticscrm.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByCustomerId(Long id);
}
