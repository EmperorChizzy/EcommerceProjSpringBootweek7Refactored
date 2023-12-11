package com.example.ecommerceprojspringbootrefactored.models;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.ecommerceprojspringbootrefactored.dtos.UsersDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String imageUrl;
    private String password;
    private String email;
    private BigDecimal balance;


    public Users(UsersDTO usersDTO) {
        this.username = usersDTO.getUsername();
        this.email = usersDTO.getEmail();
        this.password =  BCrypt.withDefaults()
                .hashToString(12, usersDTO.getPassword().toCharArray());
        this.balance = new BigDecimal(25000000);
    }
}
