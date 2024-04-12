package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.Transaction;
import org.example.data.model.User;

import java.time.LocalDate;
import java.util.List;

public interface TransactionServices {
    void save(Transaction transaction);

    List<Transaction> findAll();
}
