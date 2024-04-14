package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.BookStatus;
import org.example.data.model.Transaction;
import org.example.data.model.User;
import org.example.data.repository.Users;
import org.example.dto.request.*;
import org.example.dto.response.BorrowBookResponse;
import org.example.dto.response.RegisterUserResponse;
import org.example.dto.response.ReturnBookResponse;
import org.example.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.example.utils.Mapper.map;
import static org.example.utils.Mapper.mapp;

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
    User user = findByUsername(deleteUserRequest.getUsername().toLowerCase());
    validateLogin(user);
    users.delete(user);
    return "User Deleted";
    }

    @Override
    public BorrowBookResponse requestBook(BorrowBookRequest borrowBookRequest) {
        User user = findByUsername(borrowBookRequest.getUsername().toLowerCase());
        validateLogin(user);
        return map(user,addBookToBorrowedBook(borrowBookRequest, user));
    }


    private  Book addBookToBorrowedBook(BorrowBookRequest borrowBookRequest, User user) {
        Book book = bookServices.findBookByIsbn(borrowBookRequest.getIsbn());
        book.setMaximumDateToReturnBook(LocalDate.now().plusDays(7));
        if(user.getBalance().compareTo(BigDecimal.valueOf(1000))>=0) throw new OverDueBalanceException("You Have A Due balance, You Can't Borrow Book Until You Pay Up Your Debt");
        if(!book.isAvailable())throw new BookNotFoundException("Book Not Available At The Moment");
        book.setAvailable(false);
        bookServices.save(book);
        user.getBorrowBookList().add(book);
        createBorrowBookTransaction(user,  book);
        users.save(user);
        return book;
    }

    private void createBorrowBookTransaction(User user, Book book) {
        Transaction transaction = new Transaction();
        transaction.setBorrowedDate(LocalDate.now());
        transaction.setUser(user);
        transaction.setBookStatus(BookStatus.BORROWED);
        transaction.setBook(book);
        transactionServices.save(transaction);
    }


    @Override
    public List<Book> getBorrowedBook(String username){
        User user = findByUsername(username.toLowerCase());
        validateLogin(user);
        if(user.getBorrowBookList().isEmpty())throw new BookNotFoundException("No Book Borrowed Yet");
        return user.getBorrowBookList();
    }

    private User findByUsername(String username) {
        User user = users.findByUsername(username);
        if(user == null) throw new UserNotFoundException("User Not Found");
        return user;
    }

    @Override
    public List<Book> viewAvailableBook(String username) {
        User user = findByUsername(username.toLowerCase());
        validateLogin(user);
        return bookServices.getAvailableBooks();
    }

    @Override
    public ReturnBookResponse returnBook(ReturnBookRequest returnBookRequest) {
        User user = findByUsername(returnBookRequest.getUsername().toLowerCase());
        validateLogin(user);
        return mapp(user,removeBook(returnBookRequest, user));
    }

    private Book removeBook(ReturnBookRequest returnBookRequest, User user) {
        Book book = bookServices.findBookByIsbn(returnBookRequest.getIsbn());
        validateBorrowBook(user, book);
        if(Period.between(LocalDate.now(),book.getMaximumDateToReturnBook()).getDays()>0){
            user.setBalance(BigDecimal.valueOf(500));
            book.setMaximumDateToReturnBook(LocalDate.now().plusDays(7));
        }else{
            book.setMaximumDateToReturnBook(null);
        }
        user.setBorrowBookList(remove(user.getBorrowBookList(), book));
        users.save(user);
        book.setAvailable(true);
        bookServices.save(book);
        createReturnBookTransaction(user, book);
        return book;
    }

    private void createReturnBookTransaction(User user, Book book) {
        Transaction transaction = new Transaction();
        transaction.setReturnDate(LocalDate.now());
        transaction.setUser(user);
        transaction.setBookStatus(BookStatus.RETURNED);
        transaction.setBook(book);
        transactionServices.save(transaction);
    }

    private  List<Book> remove(List<Book> books, Book book) {
        for(Book book1: books){
            if(book1.getIsbn().equals(book.getIsbn())){
                books.remove(book1);
                break;
            }
        }
        return books;
    }

    private static void validateBorrowBook(User user, Book book) {
        boolean condition = false;
        for(Book book1 : user.getBorrowBookList()){
            if(book1.getIsbn().equals(book.getIsbn())){
                condition = true;
                break;
            }
        }
        if(!condition)throw new BookNotFoundException("Book Not Found In Borrowed Book List");
    }


    @Override
    public String login(LogInRequest logInRequest) {
        User user = findByUsername(logInRequest.getUsername().toLowerCase());
        validatePassword(logInRequest.getPassword(), user);
        user.setLoggedIn(true);
        users.save(user);
        return "Login successFul";
    }

    @Override
    public String logOut(LogOutRequest logOutRequest) {
        User user = findByUsername(logOutRequest.getUsername().toLowerCase());
        if(!user.isLoggedIn())throw new LoginException("user Already logout");
        validatePassword(logOutRequest.getPassword(), user);
        user.setLoggedIn(false);
        users.save(user);
        return "Logout Successful";
    }

    private  void validatePassword(String password, User user) {
        if(!password.equals(user.getPassword()))throw new InvalidPasswordException("Wrong password");
    }

    private void validateLogin(User user){
        if(!user.isLoggedIn())throw new LoginException("Please Login ");
    }
    private static void validateRequest(RegisterUserRequest registerUserRequest) {
        if(!registerUserRequest.getUsername().matches("[a-zA-Z0-9]+"))throw new InvalidUserNameException("Username Can Only Contain Alphabet And Number and not null");
        if(registerUserRequest.getPassword().trim().isEmpty())throw new InvalidPasswordException("Provide A Valid Password");
    }
}
