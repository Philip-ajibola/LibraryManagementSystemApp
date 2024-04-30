package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.BookStatus;
import org.example.data.model.History;
import org.example.data.model.User;
import org.example.data.repository.Users;
import org.example.dto.request.*;
import org.example.dto.response.AvailableBookResponse;
import org.example.dto.response.BorrowBookResponse;
import org.example.dto.response.RegisterUserResponse;
import org.example.dto.response.ReturnBookResponse;
import org.example.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.Mapper.*;

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
        if(user.getBalance().compareTo(BigDecimal.valueOf(500))>=0) throw new OverDueBalanceException("You Have A Due balance, You Can't Borrow Book Until You Pay Up Your Debt");
        if(!book.isAvailable())throw new BookNotFoundException("Book Not Available At The Moment");
        book.setAvailable(false);
        book.setBorrowerName(user.getUsername());
        book.setBorrowDate(LocalDate.now());
        bookServices.save(book);
        user.getBorrowBookList().add(book);
        createBorrowBookTransaction( book);
        users.save(user);
        return book;
    }

    private void createBorrowBookTransaction( Book book) {
        History history = new History();
        history.setBorrowedDate(book.getBorrowDate());
        history.setBorrowerName(book.getBorrowerName());
        history.setBookStatus(BookStatus.BORROWED);
        history.setBookId(book.getId());
        transactionServices.save(history);
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
    public List<AvailableBookResponse> viewAvailableBook(String username) {
        User user = findByUsername(username.toLowerCase());
        validateLogin(user);
        List<AvailableBookResponse> availableBookResponses = new ArrayList<>();
        for(Book book: bookServices.findAll()) availableBookResponses.add(mapAvailableBookResponse(book));
        if(availableBookResponses.isEmpty())throw new NoTransactionException("No Book Available");
        return availableBookResponses;
    }

    @Override
    public List<Book> findBookByCategory(FindByBookCategoryRequest request) {
        User user = findByUsername(request.getUsername());
        validateLogin(user);
        return bookServices.findBookByCategory(request.getCategory());
    }

    @Override
    public List<Book> findBookByAuthor(FindBookByAuthorReQuest request) {
        User user = findByUsername(request.getUsername());
        validateLogin(user);
        return bookServices.findBookByAuthor(request.getBookAuthor());
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
        if(Period.between(LocalDate.now(),book.getMaximumDateToReturnBook()).getDays()<0){
            user.setBalance(BigDecimal.valueOf(500));
            book.setMaximumDateToReturnBook(LocalDate.now().plusDays(7));
        }else{
            book.setMaximumDateToReturnBook(null);
        }
        user.setBorrowBookList(remove(user.getBorrowBookList(), book));
        users.save(user);
        book.setBorrowerName(user.getUsername());
        book.setAvailable(true);
        bookServices.save(book);
        createReturnBookTransaction(book);
        return book;
    }

    private void createReturnBookTransaction( Book book) {
        History history = new History();
        history.setReturnDate(LocalDate.now());
        history.setBorrowedDate(book.getBorrowDate());
        history.setBorrowerName(book.getBorrowerName());
        history.setBookStatus(BookStatus.RETURNED);
        history.setBookId(book.getId());
        transactionServices.save(history);
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
