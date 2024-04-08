package org.example.data.repository;

import org.example.data.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Transactions extends MongoRepository<Transaction,String> {
}
