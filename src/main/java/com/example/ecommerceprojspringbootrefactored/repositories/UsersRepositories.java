package com.example.ecommerceprojspringbootrefactored.repositories;

import com.example.ecommerceprojspringbootrefactored.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepositories extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
