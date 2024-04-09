package org.example.data.repository;

import org.example.data.model.Book;
import org.example.data.model.Transaction;
import org.example.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Transactions extends MongoRepository<Transaction,String> {
    Transaction findByUserAndBook(User user, Book book);
}
