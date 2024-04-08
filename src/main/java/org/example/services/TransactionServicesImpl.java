package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.BookStatus;
import org.example.data.model.Transaction;
import org.example.data.model.User;
import org.example.data.repository.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServicesImpl implements TransactionServices{
    @Autowired
    private Transactions transactions;
    @Override
    public void createTransaction(User user, Book book) {
        Transaction transaction = new Transaction();
        transaction.setDate(book.getLocalDate());
        transaction.setId(user.getId());
        transaction.setUser(user);
        transaction.setBook(book);
        if(book.isAvailable()) transaction.setBookStatus(BookStatus.RETURNED);
        transactions.save(transaction);
    }
}
