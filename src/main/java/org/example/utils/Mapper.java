package org.example.utils;

import org.example.data.model.History;
import org.example.data.model.Librarian;
import org.example.data.model.Book;
import org.example.data.model.User;
import org.example.dto.request.AddBookRequest;
import org.example.dto.request.RegisterAdminRequest;
import org.example.dto.request.RegisterUserRequest;
import org.example.dto.response.*;
import org.example.exception.*;
import org.example.services.TransactionServices;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.time.LocalDate;

public class Mapper {
    public static Book map(AddBookRequest bookRequest) {
        validateBookRequest(bookRequest);
        Book book = new Book();
        book.setAuthorName(bookRequest.getAuthor().toLowerCase());
        book.setTitle(bookRequest.getTitle());
        book.setIsbn(bookRequest.getIsbn());
        book.setBookCategory(bookRequest.getCategory());
        return book;
    }
    public static AddBookResponse map(Book book){
        AddBookResponse addBookResponse = new AddBookResponse();
        addBookResponse.setBookISBN(book.getIsbn());
        addBookResponse.setBookTitle(book.getTitle());
        addBookResponse.setBookId(book.getId());
        return addBookResponse;
    }
    public static Librarian map(RegisterAdminRequest registerAdminRequest) {
        validateRequest(registerAdminRequest);
        Librarian admin = new Librarian();
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        admin.setPassword(passwordEncryptor.encryptPassword(registerAdminRequest.getPassword()));
        admin.setUsername(registerAdminRequest.getUsername().toLowerCase());
        return admin;
    }
    public static  RegisterAdminResponse map(Librarian admin){
        RegisterAdminResponse response = new RegisterAdminResponse();
        response.setId(admin.getId());
        response.setUsername(admin.getUsername());
        return response;
    }
    public static AvailableBookResponse mapAvailableBookResponse(Book book) {
        AvailableBookResponse available = new AvailableBookResponse();
        available.setId(book.getId());
        available.setAuthor(book.getAuthorName());
        available.setCategory(book.getBookCategory());
        available.setIsbn(book.getIsbn());
        available.setTitle(book.getTitle());
        return available;
    }
    public static BorrowedBookResponse mapBorrowedBookResponse(Book book) {
        BorrowedBookResponse available = new BorrowedBookResponse();

        available.setId(book.getId());
        available.setIsbn(book.getIsbn());
        available.setTitle(book.getTitle());
        available.setBorrowerName(book.getBorrowerName());
        available.setMaximumDayToReturnBook(book.getMaximumDateToReturnBook());
        return available;
    }
    public static BorrowBookResponse map(User user, Book book) {
        BorrowBookResponse response = new BorrowBookResponse();
        response.setBookTitle(book.getTitle());
        response.setIsbn(book.getIsbn());
        response.setBookId(book.getId());
        response.setCategory(book.getBookCategory());
        response.setBookAuthor(book.getAuthorName());
        response.setMaximumDateToReturnBook(book.getMaximumDateToReturnBook());
        return response;
    }
    public static ReturnBookResponse mapp(User user, Book book) {
        ReturnBookResponse response = new ReturnBookResponse();
        response.setBookTitle(book.getTitle());
        response.setBookIsbn(book.getIsbn());
        response.setBorrowerId(user.getId());
        response.setBorrowerName(user.getUsername());
        response.setBookId(book.getId());
        response.setAvailable(book.isAvailable());
        response.setReturnedDate(LocalDate.now());
        return response;
    }
    public static User map(RegisterUserRequest registerUserRequest) {
        User user = new User();
        StrongPasswordEncryptor passwordEncryptor =  new StrongPasswordEncryptor();
        user.setPassword(passwordEncryptor.encryptPassword(registerUserRequest.getPassword()));
        user.setUsername(registerUserRequest.getUsername().toLowerCase());
        return user;
    }
    public static RegisterUserResponse map(User user){
        RegisterUserResponse response = new RegisterUserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        return response;
    }


    private static void validateBookRequest(AddBookRequest bookRequest) {
        if(bookRequest.getTitle().isEmpty())throw new InvalidBookTitleException("Provide A Book Title");
        if(bookRequest.getIsbn().isEmpty()|| bookRequest.getIsbn().length()<10 ||  bookRequest.getIsbn().length()>13 )throw new InvalidISBNNumberException("Provide A valid Isbn number");
        if(bookRequest.getAuthor().isEmpty()||!bookRequest.getAuthor().matches("[a-zA-Z\\s]+"))throw new InvalidAuthorNameException("Provide A Valid AuthorName ");
        if(bookRequest.getCategory() == null) throw new BookCategoryException("Book Category can't be null");
    }
    private static void validateRequest(RegisterAdminRequest registerAdminRequest) {
        if(!registerAdminRequest.getUsername().matches("[a-zA-Z0-9]+"))throw new InvalidUserNameException("Username Can Only Contain Alphabet And Number and not null");
        if(registerAdminRequest.getPassword().trim().isEmpty())throw new InvalidPasswordException("Provide A Valid Password");
    }

}
