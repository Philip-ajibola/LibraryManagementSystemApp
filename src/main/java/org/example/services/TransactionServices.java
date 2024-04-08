package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.User;

public interface TransactionServices {
    void createTransaction(User user, Book book);
}
