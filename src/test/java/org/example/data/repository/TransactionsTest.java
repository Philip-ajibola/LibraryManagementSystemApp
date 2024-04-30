package org.example.data.repository;

import org.example.data.model.History;
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
        History history = new History();
        history.setBookStatus(BookStatus.RETURNED);
        transactions.save(history);
    }
}