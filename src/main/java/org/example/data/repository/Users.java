package org.example.data.repository;

import org.example.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Users extends MongoRepository<User,String> {

    User findByUsername(String username);
}
