package com.roseny.logisticscrm.repositories;

import com.roseny.logisticscrm.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
