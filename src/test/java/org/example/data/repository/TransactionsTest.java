package org.example.data.repository;

import org.example.data.model.Transaction;
import org.example.data.model.BookStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionsTest {
    @Autowired
    private Transactions transactions;
    @Test
    public void testThatTransactionCanBeSaved(){
        Transaction transaction = new Transaction();
        transaction.setBookStatus(BookStatus.RETURNED);
        transactions.save(transaction);
    }
}