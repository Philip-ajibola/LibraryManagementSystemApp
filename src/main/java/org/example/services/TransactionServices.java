package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.User;

import java.time.LocalDate;

public interface TransactionServices {
    void createTransaction(User user, Book book);
    void addBorrowedDate(User user, Book book, LocalDate localDate);
    void addReturnDate(User user,Book book, LocalDate localDate);
}
