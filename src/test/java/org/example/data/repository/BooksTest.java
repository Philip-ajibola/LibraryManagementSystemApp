package org.example.data.repository;

import org.example.data.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class BooksTest {
    @Autowired
    private Books books;
    @Test
    public void testThatBookCanBeSaved(){
        Book book = new Book();
        book.setIsbn("1256778961");
        book.setAvailable(true);
        book.setTitle("title");
        book.setAuthorName("author");
        books.save(book);
        assertEquals(1,books.count());
    }


}