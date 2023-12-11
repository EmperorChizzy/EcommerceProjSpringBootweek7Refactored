package com.example.ecommerceprojspringbootrefactored.models;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.ecommerceprojspringbootrefactored.dtos.AdminDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String image;

    public Admin(AdminDto adminDto){
        this.username = adminDto.getUsername();
        this.email = adminDto.getEmail();
        this.password = BCrypt.withDefaults()
                .hashToString(12, adminDto.getPassword().toCharArray());
    }
}