package org.example.services;

import org.example.data.model.*;
import org.example.data.repository.LibrarianRepository;
import org.example.data.repository.Books;
import org.example.data.repository.Transactions;
import org.example.dto.request.*;
import org.example.exception.BookCategoryException;
import org.example.exception.InvalidPasswordException;
import org.example.exception.InvalidUserNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class AdminServicesTest {
    @Autowired
    private LibraryServices libraryServices;
    @Autowired
    private Books books;
    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private Transactions transactions;
    private LogInRequest logInRequest;
    private RegisterAdminRequest registerAdminRequest;
    @BeforeEach
    public void initializer(){
        transactions.deleteAll();
        librarianRepository.deleteAll();
        books.deleteAll();
        logInRequest = new LogInRequest();
        registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setPassword("password");
        registerAdminRequest.setUsername("username");
        libraryServices.registerAdmin(registerAdminRequest);
        logInRequest.setPassword(registerAdminRequest.getPassword());
        logInRequest.setUsername(registerAdminRequest.getUsername());
        libraryServices.login(logInRequest);
    }
    @Test
    public void testThatAdminCanBeCreated(){
         assertNotNull(librarianRepository.findByUsername(registerAdminRequest.getUsername()));
    }
    @Test
    public void testThatAdminCanLogin(){
        libraryServices.login(logInRequest);
        assertTrue(librarianRepository.findByUsername(registerAdminRequest.getUsername()).isLoggedIn());
    }
    @Test
    public void testThatAdminCanLogOut(){
        LogOutRequest logOutRequest = new LogOutRequest();
        logOutRequest.setPassword(registerAdminRequest.getPassword());
        logOutRequest.setUsername(registerAdminRequest.getUsername());
        libraryServices.logout(logOutRequest);
        assertFalse(librarianRepository.findByUsername(registerAdminRequest.getUsername()).isLoggedIn());
    }
    @Test
    public void testThatWhenUserNameIsInValidExceptionIsThrown(){
        registerAdminRequest.setUsername("");
        assertThrows(InvalidUserNameException.class,()-> libraryServices.registerAdmin(registerAdminRequest));
    }
    @Test
    public void testThatWhenPasswordIsInValidExceptionIsThrown(){
        registerAdminRequest.setPassword("");
        assertThrows(InvalidPasswordException.class,()-> libraryServices.registerAdmin(registerAdminRequest));
    }
    @Test
    public void testThatAdminAccountCanBeReset(){
        ResetAdminRequest resetAdminRequest = new ResetAdminRequest();
        resetAdminRequest.setNewUsername("newUsername");
        resetAdminRequest.setNewPassword("newPassword");
        resetAdminRequest.setOldPassword("password");
        System.out.println(registerAdminRequest.getPassword());
        resetAdminRequest.setOldUsername(registerAdminRequest.getUsername());
        libraryServices.resetAdmin(resetAdminRequest);
        assertEquals(1, librarianRepository.count());
    }
    @Test
    public void testThatAdminCanAddBookToBookShelf(){
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setAuthor("author");
        bookRequest.setTitle("title");
        bookRequest.setIsbn("1234568794");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        bookRequest.setCategory(BookCategory.CHILDREN_BOOK);
        libraryServices.addBook(bookRequest);
        assertEquals(1,books.count());
    }
    @Test
    public void testThatAdminCanDeleteBook(){
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setAuthor("author");
        bookRequest.setTitle("title");
        bookRequest.setIsbn("1234568794");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        bookRequest.setCategory(BookCategory.CHILDREN_BOOK);
        libraryServices.addBook(bookRequest);
        DeleteBookRequest deleteBookRequest = new DeleteBookRequest();
        deleteBookRequest.setIsbn(bookRequest.getIsbn());
        deleteBookRequest.setAdminName(registerAdminRequest.getUsername());
        libraryServices.deleteBook(deleteBookRequest);
        assertEquals(0,books.count());
    }
    @Test
    public void testThatAdminCanGetTransactionHistory(){
        User user  = new User();
        History history = new History();
        Book book = new Book();
        history.setBookStatus(BookStatus.BORROWED);
        history.setBookId(book.getId());
        history.setBorrowerName(user.getUsername());
        transactions.save(history);
        assertEquals(1, libraryServices.getBooksHistory(registerAdminRequest.getUsername()).size());
    }
    @Test
    public void testThatAdminCanBeChange(){
        ResetAdminRequest resetAdminRequest = new ResetAdminRequest();
        resetAdminRequest.setNewPassword("newPassword");
        resetAdminRequest.setNewUsername("newAdmin");
        resetAdminRequest.setOldUsername(registerAdminRequest.getUsername());
        resetAdminRequest.setOldPassword("password");
        libraryServices.resetAdmin(resetAdminRequest);
        assertEquals(1, librarianRepository.count());
    }
    @Test
    public void testThatAdminCanGetListOfAvailableBook(){
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setAuthor("author");
        bookRequest.setTitle("title");
        bookRequest.setIsbn("1234568794");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        bookRequest.setCategory(BookCategory.CHILDREN_BOOK);
        libraryServices.addBook(bookRequest);
       assertEquals(1, libraryServices.getAvailablebooks(registerAdminRequest.getUsername()).size());

    }
    @Test
    public void testThatAdminCanGetListOfBorrowedBook(){
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setAuthor("author");
        bookRequest.setTitle("title");
        bookRequest.setIsbn("1234568794");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        bookRequest.setCategory(BookCategory.CHILDREN_BOOK);
        libraryServices.addBook(bookRequest);
        Book book = books.findByIsbn("1234568794");
        book.setAvailable(false);
        books.save(book);
        assertEquals(1, libraryServices.getBorrowedBook(registerAdminRequest.getUsername()).size());
        assertEquals(1,books.count());
    }
    @Test
    public void testThatWhenBookCategoryIsNotAddedExceptionIsThrown(){
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setAuthor("author");
        bookRequest.setTitle("title");
        bookRequest.setIsbn("1234568794");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        assertThrows(BookCategoryException.class,()->libraryServices.addBook(bookRequest));
    }




 }