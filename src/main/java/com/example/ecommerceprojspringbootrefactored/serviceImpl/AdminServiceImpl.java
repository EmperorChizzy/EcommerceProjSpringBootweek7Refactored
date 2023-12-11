package com.example.ecommerceprojspringbootrefactored.serviceImpl;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.ecommerceprojspringbootrefactored.dtos.PasswordDTO;
import com.example.ecommerceprojspringbootrefactored.models.Admin;
import com.example.ecommerceprojspringbootrefactored.models.Users;
import com.example.ecommerceprojspringbootrefactored.repositories.AdminRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
public class AdminServiceImpl {
    private AdminRepositories adminRepositories;

    @Autowired
    public AdminServiceImpl(AdminRepositories adminRepositories) {
        this.adminRepositories = adminRepositories;
    }


    public Function<String, Admin> findAdminByEmail = (email)->
            adminRepositories.findByEmail(email)
                    .orElseThrow(()->new NullPointerException("Admin not found!"));
    public Function<Long, Admin> findAdminById = (id)->
            adminRepositories.findById(id)
                    .orElseThrow(()->new NullPointerException("Admin not found!"));

    public Function<Admin, Admin> saveAdmin = (admin)->adminRepositories.save(admin);

    public Function<PasswordDTO, Boolean> verifyAdminPassword = passwordDTO -> BCrypt.verifyer()
            .verify(passwordDTO.getPassword().toCharArray(),
                    passwordDTO.getHashPassword().toCharArray())
            .verified;
}

