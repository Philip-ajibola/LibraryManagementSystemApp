package org.example.services;

import org.example.data.repository.AdminRepository;
import org.example.data.repository.Books;
import org.example.dto.AddBookRequest;
import org.example.dto.DeleteBookRequest;
import org.example.exception.BookNotFoundException;
import org.example.exception.InvalidAuthorNameException;
import org.example.exception.InvalidBookTitleException;
import org.example.exception.InvalidISBNNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class BookServicesTest {
    @Autowired
    private BookServices bookServices;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private Books books;
    private AddBookRequest bookRequest;
    @BeforeEach
    public void initializer(){
        adminRepository.deleteAll();
        books.deleteAll();
         bookRequest = new AddBookRequest();
        bookRequest.setAuthor("author");
        bookRequest.setIsbn("1234567868");
        bookRequest.setTitle("title");
    }

    @Test
    public void testThatBookCanBeAdded() {
        bookServices.addbook(bookRequest);
        assertEquals(1,books.count());
    }
    @Test
    public void testIfBookTitleIsEmptyExceptionIsThrown(){
        bookRequest.setTitle("");
        assertThrows(InvalidBookTitleException.class,()->bookServices.addbook(bookRequest));
    }
    @Test
    public void testThatWhenTheBookISBNIsEmptyExceptionIsThrown(){
        bookRequest.setIsbn("12234567891012");
        assertThrows(InvalidISBNNumberException.class,()->bookServices.addbook(bookRequest));
    }
    @Test
    public void testThatWhenBookAuthorIsEmptyExceptionIsThrown(){
        bookRequest.setAuthor("Mike 6Judge");
        assertThrows(InvalidAuthorNameException.class,()->bookServices.addbook(bookRequest));
    }
    @Test
    public void testThatBookCanBeDeleted(){
        bookServices.addbook(bookRequest);
        DeleteBookRequest deleteBookRequest = new DeleteBookRequest();
        deleteBookRequest.setIsbn(bookRequest.getIsbn());
        bookServices.deleteBook(deleteBookRequest);
        assertEquals(0,books.count());
    }
    @Test
    public void testThatWhenBookIsNotFoundExceptionIsThrown(){
        bookServices.addbook(bookRequest);
        DeleteBookRequest deleteBookRequest = new DeleteBookRequest();
        deleteBookRequest.setIsbn("5678910432");
        assertThrows(BookNotFoundException.class,()->bookServices.deleteBook(deleteBookRequest));
    }

}