package org.example.data.repository;

import org.example.data.model.Librarian;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LibrarianRepository extends MongoRepository<Librarian,String> {
    Librarian findByUsername(String username);
}
