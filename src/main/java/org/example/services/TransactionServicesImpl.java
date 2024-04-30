package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.History;
import org.example.data.repository.Books;
import org.example.data.repository.Transactions;
import org.example.exception.NoBookHistoryFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServicesImpl implements TransactionServices{
    @Autowired
    private Transactions transactions;
    @Autowired
    private Books books;

    @Override
    public void save(History history) {
        transactions.save(history);
    }

    @Override
    public List<History> findAll() {
        return transactions.findAll();
    }



}
