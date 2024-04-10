package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.Transaction;
import org.example.data.model.User;

import java.time.LocalDate;

public interface TransactionServices {
    void createBorrowBookTransaction(User user, Book book,LocalDate localDate);
    void createReturnBookTransaction(User user, Book book,LocalDate localDate);

    void save(Transaction transaction);
}
