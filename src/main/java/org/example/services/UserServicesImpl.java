package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.User;
import org.example.data.repository.Users;
import org.example.dto.*;
import org.example.dto.response.BookResponse;
import org.example.dto.response.RegisterUserResponse;
import org.example.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.example.utils.Mapper.map;

@Service
public class UserServicesImpl implements  UserServices{
    @Autowired
    private Users users;
    @Autowired
    private BookServices bookServices;
    @Autowired
    private TransactionServices transactionServices;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        validateRequest(registerUserRequest);
        User user =users.save(map(registerUserRequest));
        users.save(user);
        return map(user);
    }



    @Override
    public String deleteUser(DeleteUserRequest deleteUserRequest) {
    User user = users.findByUsername(deleteUserRequest.getUsername().toLowerCase());
    validateUser(user);
    users.delete(user);
    return "User Deleted";
    }

    @Override
    public BookResponse requestBook(BorrowBookRequest borrowBookRequest) {
        User user = users.findByUsername(borrowBookRequest.getUsername().toLowerCase());
        validateUser(user);
        validateLogin(user);
        return map(user,addBookToBorrowedBook(borrowBookRequest, user));
    }

    private  Book addBookToBorrowedBook(BorrowBookRequest borrowBookRequest, User user) {
        Book book = bookServices.findBookByIsbn(borrowBookRequest.getIsbn());
        if(!book.isAvailable())throw new BookNotFoundException("Book Not Available At The Moment");
        book.setAvailable(false);
        book.setLocalDate(LocalDate.now());
        transactionServices.createTransaction(user,book);
        bookServices.save(book);
        List<Book> borrowedBookList = user.getBorrowBookList();
        borrowedBookList.add(book);
        user.setBorrowBookList(borrowedBookList);
        users.save(user);
        return book;
    }
    @Override
    public List<Book> getBorrowedBook(String username){
        User user = users.findByUsername(username.toLowerCase());
        validateUser(user);
        if(user.getBorrowBookList().isEmpty())throw new BookNotFoundException("No Book Borrowed Yet");
        return user.getBorrowBookList();
    }

    @Override
    public BookResponse returnBook(ReturnBookRequest returnBookRequest) {
        User user = users.findByUsername(returnBookRequest.getUsername().toLowerCase());
        validateUser(user);
        validateLogin(user);
        return map(user,removeBook(returnBookRequest, user));
    }

    private Book removeBook(ReturnBookRequest returnBookRequest, User user) {
        List<Book> borrowedBookList = user.getBorrowBookList();
        Book book = bookServices.findBookByIsbn(returnBookRequest.getIsbn());
        if(book == null) throw new BookNotFoundException("Book not found");
        book.setLocalDate(LocalDate.now());
        borrowedBookList.remove(book);
        user.setBorrowBookList(borrowedBookList);
        book.setAvailable(true);
        transactionServices.createTransaction(user,book);
        bookServices.save(book);
        users.save(user);
        return book;
    }

    @Override
    public String login(LogInRequest logInRequest) {
        User user = users.findByUsername(logInRequest.getUsername().toLowerCase());
        validateUser(user);
        validatePassword(logInRequest.getPassword(), user);
        user.setLoggedIn(true);
        users.save(user);
        return "Login successFul";
    }

    @Override
    public String logOut(LogOutRequest logOutRequest) {
        User user = users.findByUsername(logOutRequest.getUsername().toLowerCase());
        validateUser(user);
        validatePassword(logOutRequest.getPassword(), user);
        user.setLoggedIn(false);
        users.save(user);
        return "Logout Successful";
    }

    private  void validatePassword(String password, User user) {
        if(!password.equals(user.getPassword()))throw new InvalidPasswordException("Wrong password");
    }

    private void validateUser(User user) {
        if(user == null)throw new UserNotFoundException("User Not Found");
    }

    private void validateLogin(User user){
        if(!user.isLoggedIn())throw new LoginException("Please Login ");
    }
    private static void validateRequest(RegisterUserRequest registerUserRequest) {
        if(!registerUserRequest.getUsername().matches("[a-zA-Z0-9]+"))throw new InvalidUserNameException("Username Can Only Contain Alphabet And Number and not null");
        if(registerUserRequest.getPassword().trim().isEmpty())throw new InvalidPasswordException("Provide A Valid Password");
    }
}
