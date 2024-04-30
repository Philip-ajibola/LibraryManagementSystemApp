package org.example.data.repository;

import org.example.data.model.Book;
import org.example.data.model.History;
import org.example.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Transactions extends MongoRepository<History,String> {
}
