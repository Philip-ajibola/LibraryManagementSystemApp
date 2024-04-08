package org.example.data.repository;

import org.example.data.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UsersTest {
    @Autowired
    private Users users;
    @Test
    public void testThatUserCanBeSaved(){
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        users.save(user);
    }

}