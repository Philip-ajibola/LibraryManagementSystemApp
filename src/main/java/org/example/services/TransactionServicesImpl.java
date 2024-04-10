package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.BookStatus;
import org.example.data.model.Transaction;
import org.example.data.model.User;
import org.example.data.repository.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionServicesImpl implements TransactionServices{
    @Autowired
    private Transactions transactions;
    @Override
    public void createBorrowBookTransaction(User user, Book book,LocalDate localDate) {
        Transaction transaction = new Transaction();
        transaction.setId(user.getId());
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setBorrowedDate(localDate);
        transaction.setBookStatus(BookStatus.BORROWED);
        transactions.save(transaction);
    }

    @Override
    public void createReturnBookTransaction(User user, Book book, LocalDate localDate) {
        Transaction transaction = new Transaction();
        transaction.setId(user.getId());
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setBorrowedDate(localDate);
        transaction.setBookStatus(BookStatus.RETURNED);
        transactions.save(transaction);
    }

    @Override
    public void save(Transaction transaction) {
        transactions.save(transaction);
    }


}
