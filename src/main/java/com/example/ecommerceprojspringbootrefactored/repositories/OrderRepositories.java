package com.example.ecommerceprojspringbootrefactored.repositories;

import com.example.ecommerceprojspringbootrefactored.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepositories extends JpaRepository<Order, Long> {
}
