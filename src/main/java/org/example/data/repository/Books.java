package org.example.data.repository;

import org.example.data.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Books extends MongoRepository<Book,String> {
    void deleteByIsbn(String isbn);

    Book findByIsbn(String isbn);
}
