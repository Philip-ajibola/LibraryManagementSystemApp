package org.example.data.repository;

import org.example.data.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin,String> {
    Admin findByUsername(String username);
}
