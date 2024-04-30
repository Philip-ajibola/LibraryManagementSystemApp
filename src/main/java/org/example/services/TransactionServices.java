package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.History;

import java.util.List;

public interface TransactionServices {
    void save(History history);

    List<History> findAll();
}
