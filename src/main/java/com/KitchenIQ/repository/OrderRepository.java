package com.KitchenIQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KitchenIQ.model.OrderHistory;

@Repository
public interface OrderRepository extends JpaRepository<OrderHistory, Long> {
    long countByStatus(String status);
}