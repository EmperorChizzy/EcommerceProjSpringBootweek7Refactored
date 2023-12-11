package com.example.ecommerceprojspringbootrefactored.serviceImpl;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.example.ecommerceprojspringbootrefactored.dtos.PasswordDTO;
import com.example.ecommerceprojspringbootrefactored.models.Users;
import com.example.ecommerceprojspringbootrefactored.repositories.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UsersServiceImpl {

    private UsersRepositories usersRepositories;

    @Autowired
    public UsersServiceImpl(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }

    public Function<String, Users> findUsersByEmail = (email)->
            usersRepositories.findByEmail(email)
                    .orElseThrow(()->new NullPointerException("User not found!"));
    public Function<Long, Users> findUsersById = (id)->
            usersRepositories.findById(id)
                    .orElseThrow(()->new NullPointerException("User not found!"));

    public Function<Users, Users> saveUser = (user)->usersRepositories.save(user);

    public Function<PasswordDTO, Boolean> verifyUserPassword = passwordDTO -> BCrypt.verifyer()
            .verify(passwordDTO.getPassword().toCharArray(),
                    passwordDTO.getHashPassword().toCharArray())
            .verified;
}
