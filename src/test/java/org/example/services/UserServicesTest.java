package org.example.services;

import org.example.data.repository.AdminRepository;
import org.example.data.repository.Books;
import org.example.data.repository.Users;
import org.example.dto.request.*;
import org.example.exception.BookNotFoundException;
import org.example.exception.InvalidPasswordException;
import org.example.exception.InvalidUserNameException;
import org.example.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServicesTest {
    private RegisterUserRequest registerUserRequest;

    @Autowired
    private UserServices userServices;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private Books books;
    @Autowired
    private Users users;
    private RegisterAdminRequest registerAdminRequest;
    @Autowired
    private AdminServices adminServices;
    @BeforeEach
    public void initializer(){
        adminRepository.deleteAll();
        registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setUsername("username");
        registerAdminRequest.setPassword("password");

        adminServices.registerAdmin(registerAdminRequest);

        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setPassword(registerAdminRequest.getPassword());
        logInRequest.setUsername(registerAdminRequest.getUsername());
        adminServices.login(logInRequest);


        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setPassword("password");
        registerUserRequest.setUsername("username");


        books.deleteAll();;
        users.deleteAll();
    }
    @Test
    public void testThatUserCanRegister(){
        userServices.registerUser(registerUserRequest);
        assertEquals(1,users.count());
    }
    @Test
    public void testThatWhenUsernameIsEmptyExceptionIsThrown(){
        registerUserRequest.setUsername("");
        assertThrows(InvalidUserNameException.class,()->userServices.registerUser(registerUserRequest));
    }
    @Test
    public void testThatWhenPasswordIsEmptyErrorMessageIsThrown(){
        registerUserRequest.setPassword("");
        assertThrows(InvalidPasswordException.class,()->userServices.registerUser(registerUserRequest));
    }
    @Test
    public void testThatUserCanBeDeleted(){
        userServices.registerUser(registerUserRequest);
        LogInRequest logInRequest1 = new LogInRequest();
        logInRequest1.setPassword(registerUserRequest.getPassword());
        logInRequest1.setUsername(registerUserRequest.getUsername());
        userServices.login(logInRequest1);
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUsername(registerUserRequest.getUsername());
        userServices.deleteUser(deleteUserRequest);
        assertEquals(0,users.count());
    }
    @Test
    public void testThatWhenUserIsNotFoundErrorMessageIsThrown(){
        userServices.registerUser(registerUserRequest);
        LogInRequest logInRequest1 = new LogInRequest();
        logInRequest1.setPassword(registerUserRequest.getPassword());
        logInRequest1.setUsername(registerUserRequest.getUsername());
        userServices.login(logInRequest1);
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUsername("user");
        assertThrows(UserNotFoundException.class,()->userServices.deleteUser(deleteUserRequest));
    }
    @Test
    public void testThatUserCanLogin(){
        userServices.registerUser(registerUserRequest);
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUsername(registerUserRequest.getUsername());
        logInRequest.setPassword(registerUserRequest.getPassword());
        userServices.login(logInRequest);
        assertTrue(users.findByUsername(registerUserRequest.getUsername()).isLoggedIn());
    }
    @Test
    public void testThatWhenUserThatDoesNotExistTryToLogin_ExceptionIsThrown(){
        userServices.registerUser(registerUserRequest);
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUsername("wrongUsername");
        logInRequest.setPassword(registerUserRequest.getPassword());
        assertThrows(UserNotFoundException.class,()->userServices.login(logInRequest));
    }
    @Test
    public void testThatWhenUserTrysToLoginWithInvalidPassword_ExceptionIsThrown(){
        userServices.registerUser(registerUserRequest);
        LogInRequest logInRequest = new LogInRequest();
        logInRequest.setUsername(registerUserRequest.getUsername());
        logInRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class,()->userServices.login(logInRequest));
    }
    @Test
    public void testThatUserCanLogOut(){
        userServices.registerUser(registerUserRequest);
        LogOutRequest logOutRequest = new LogOutRequest();
        logOutRequest.setUsername(registerUserRequest.getUsername());
        logOutRequest.setPassword(registerUserRequest.getPassword());
        userServices.logOut(logOutRequest);
        assertFalse(users.findByUsername(registerUserRequest.getUsername()).isLoggedIn());
    }

    @Test
    public void testThatUserCanRequestForBook(){
        userServices.registerUser(registerUserRequest);

        LogInRequest logInRequest1 = new LogInRequest();
        logInRequest1.setPassword(registerUserRequest.getPassword());
        logInRequest1.setUsername(registerUserRequest.getUsername());
        userServices.login(logInRequest1);

        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setIsbn("1234567584902");
        bookRequest.setTitle("title");
        bookRequest.setAuthor("author");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        adminServices.addBook(bookRequest);
        BorrowBookRequest borrowBookRequest =new BorrowBookRequest();
        borrowBookRequest.setIsbn("1234567584902");
        borrowBookRequest.setUsername(registerUserRequest.getUsername());
        userServices.requestBook(borrowBookRequest);
        assertEquals(1,books.count());
        assertEquals(1,users.findByUsername(registerUserRequest.getUsername()).getBorrowBookList().size());
    }
    @Test
    public void testThatWhenBookIsNotAvailableAndUserTryToBorrowExceptionIsThrown(){
        userServices.registerUser(registerUserRequest);
        LogInRequest logInRequest1 = new LogInRequest();
        logInRequest1.setPassword(registerUserRequest.getPassword());
        logInRequest1.setUsername(registerUserRequest.getUsername());
        userServices.login(logInRequest1);
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setIsbn("1234567584902");
        bookRequest.setTitle("title");
        bookRequest.setAuthor("author");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        adminServices.addBook(bookRequest);
        BorrowBookRequest borrowBookRequest =new BorrowBookRequest();
        borrowBookRequest.setIsbn("1234567984902");
        borrowBookRequest.setUsername(registerUserRequest.getUsername());

        assertThrows(BookNotFoundException.class,()->userServices.requestBook(borrowBookRequest));
    }
    @Test
    public void testThatUserCanReturnBook(){
        userServices.registerUser(registerUserRequest);
        LogInRequest logInRequest1 = new LogInRequest();
        logInRequest1.setPassword(registerUserRequest.getPassword());
        logInRequest1.setUsername(registerUserRequest.getUsername());
        userServices.login(logInRequest1);
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setIsbn("1234567584902");
        bookRequest.setTitle("title");
        bookRequest.setAuthor("author");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        adminServices.addBook(bookRequest);
        BorrowBookRequest borrowBookRequest =new BorrowBookRequest();
        borrowBookRequest.setIsbn("1234567584902");
        borrowBookRequest.setUsername(registerUserRequest.getUsername());
        userServices.requestBook(borrowBookRequest);
        ReturnBookRequest returnBookRequest = new ReturnBookRequest();
        returnBookRequest.setIsbn("1234567584902");
        returnBookRequest.setUsername(registerUserRequest.getUsername());
        userServices.returnBook(returnBookRequest);
        assertEquals(1,books.count());
        assertEquals(0,users.findByUsername(registerUserRequest.getUsername()).getBorrowBookList().size());
    }
    @Test
    public void testThatWhenUserTryToReturnBookThatHeDidntBorrowExceptionIsThrown(){
        userServices.registerUser(registerUserRequest);
        LogInRequest logInRequest1 = new LogInRequest();
        logInRequest1.setPassword(registerUserRequest.getPassword());
        logInRequest1.setUsername(registerUserRequest.getUsername());
        userServices.login(logInRequest1);
        AddBookRequest bookRequest = new AddBookRequest();
        bookRequest.setIsbn("1234567584902");
        bookRequest.setTitle("title");
        bookRequest.setAuthor("author");
        bookRequest.setAdminName(registerAdminRequest.getUsername());
        adminServices.addBook(bookRequest);
        BorrowBookRequest borrowBookRequest =new BorrowBookRequest();
        borrowBookRequest.setIsbn("1234567584902");
        borrowBookRequest.setUsername(registerUserRequest.getUsername());
        userServices.requestBook(borrowBookRequest);
        ReturnBookRequest returnBookRequest = new ReturnBookRequest();
        returnBookRequest.setIsbn("1234567084902");
        returnBookRequest.setUsername(registerUserRequest.getUsername());
        assertThrows(BookNotFoundException.class,()->userServices.returnBook(returnBookRequest));
    }
}