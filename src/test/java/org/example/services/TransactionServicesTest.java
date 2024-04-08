package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.Transaction;
import org.example.data.model.BookStatus;
import org.example.data.model.User;
import org.example.data.repository.Transactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TransactionServicesTest {
    @Autowired
    private Transactions transactions;

    @Test
    public void testThatTransactionCanBeAdded(){
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        Book book = new Book();
        book.setIsbn("1234567895");
        book.setTitle("title");
        book.setAuthorName("author");
        Transaction transaction = new Transaction();
        transaction.setBookStatus(BookStatus.RETURNED);
        transaction.setBook(book);
        transaction.setUser(user);
        transactions.save(transaction);
        assertEquals(1,transactions.count());
    }


}