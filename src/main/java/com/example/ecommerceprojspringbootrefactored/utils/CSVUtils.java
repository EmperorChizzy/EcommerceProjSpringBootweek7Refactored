package com.example.ecommerceprojspringbootrefactored.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.ecommerceprojspringbootrefactored.models.Admin;
import com.example.ecommerceprojspringbootrefactored.models.Product;
import com.example.ecommerceprojspringbootrefactored.models.Users;
import com.example.ecommerceprojspringbootrefactored.repositories.AdminRepositories;
import com.example.ecommerceprojspringbootrefactored.repositories.ProductRepositories;
import com.example.ecommerceprojspringbootrefactored.repositories.UsersRepositories;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

@Component
public class CSVUtils {

    private final UsersRepositories usersRepositories;
    private final ProductRepositories productRepositories;
    private final AdminRepositories adminRepositories;

    @Autowired
    public CSVUtils(UsersRepositories usersRepositories, ProductRepositories productRepositories, AdminRepositories adminRepositories) {
        this.usersRepositories = usersRepositories;
        this.productRepositories = productRepositories;
        this.adminRepositories = adminRepositories;
    }

    @PostConstruct
    public void readUserCSV(){

        //user database seeding
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/com/example/ecommerceprojspringbootrefactored/users.csv"))) {
            String line;
            boolean lineOne = false;
            while ((line=bufferedReader.readLine())!=null){
                String[]user = line.split(",");
                if (lineOne) {
                    Users user1 = Users.builder()
                            .email(user[1])
                            .imageUrl(user[3])
                            .password(BCrypt.withDefaults().hashToString(12, user[2].trim().toCharArray()))
                            .username(user[0])
                            .balance(new BigDecimal(2500000))
                            .build();
                    usersRepositories.save(user1);
                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try
                (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/com/example/ecommerceprojspringbootrefactored/admin.csv")))
        {
            String line;
            boolean isFirstLine = false;
            while ((line = bufferedReader.readLine()) != null){
                String[] admin = line.split(",");
                if(isFirstLine) {
                    Admin admin1 = Admin.builder()
                            .email(admin[1])
                            .username(admin[0])
                            .password(BCrypt.withDefaults().hashToString(12, admin[2].trim().toCharArray()))
                            .image(admin[3])
                            .build();
                    adminRepositories.save(admin1);

                }
                isFirstLine = true;
            }

        }catch(IOException e){
        throw new RuntimeException(e);
    }


        //product database seeding
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/com/example/ecommerceprojspringbootrefactored/product.csv"))) {
            String line;
            boolean lineOne = false;
            while ((line=bufferedReader.readLine())!=null){
                String[]product = line.split(",");
                if (lineOne) {
                    Product product1 = Product.builder()
                            .categories(product[0])
                            .price(new BigDecimal(product[1]))
                            .productName(product[2])
                            .quantity(Long.parseLong(product[3]))
                            .build();
                    productRepositories.save(product1);
                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
