package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.BookStatus;
import org.example.data.model.Transaction;
import org.example.data.model.User;
import org.example.data.repository.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServicesImpl implements TransactionServices{
    @Autowired
    private Transactions transactions;

    @Override
    public void save(Transaction transaction) {
        transactions.save(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        return transactions.findAll();
    }


}
