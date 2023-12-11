package com.example.ecommerceprojspringbootrefactored.repositories;
import com.example.ecommerceprojspringbootrefactored.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepositories extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);

}
