package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.History;
import org.example.data.model.BookStatus;
import org.example.data.model.User;
import org.example.data.repository.Transactions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class HistoryServicesTest {
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
        History history = new History();
        history.setBookStatus(BookStatus.RETURNED);
        history.setBookId(book.getId());
        history.setBorrowerName(user.getUsername());
        transactions.save(history);
        assertEquals(1,transactions.count());
    }


}