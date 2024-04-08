package org.example.utils;

import org.example.data.model.Admin;
import org.example.data.model.Book;
import org.example.data.model.User;
import org.example.dto.AddBookRequest;
import org.example.dto.RegisterAdminRequest;
import org.example.dto.RegisterUserRequest;
import org.example.dto.response.AddBookResponse;
import org.example.dto.response.RegisterAdminResponse;
import org.example.dto.response.BookResponse;
import org.example.dto.response.RegisterUserResponse;
import org.example.exception.*;

public class Mapper {
    public static Book map(AddBookRequest bookRequest) {
        validateBookRequest(bookRequest);
        Book book = new Book();
        book.setAuthorName(bookRequest.getAuthor());
        book.setTitle(bookRequest.getTitle());
        book.setIsbn(bookRequest.getIsbn());
        return book;
    }
    public static AddBookResponse map(Book book){
        AddBookResponse addBookResponse = new AddBookResponse();
        addBookResponse.setBookISBN(book.getIsbn());
        addBookResponse.setBookTitle(book.getTitle());
        addBookResponse.setBookId(book.getId());
        return addBookResponse;
    }
    public static Admin map(RegisterAdminRequest registerAdminRequest) {
        validateRequest(registerAdminRequest);
        Admin admin = new Admin();
        admin.setPassword(registerAdminRequest.getPassword());
        admin.setUsername(registerAdminRequest.getUsername().toLowerCase());
        return admin;
    }
    public static  RegisterAdminResponse map(Admin admin){
        RegisterAdminResponse response = new RegisterAdminResponse();
        response.setId(admin.getId());
        response.setUsername(admin.getUsername());
        return response;
    }
    public static BookResponse map(User user, Book book) {
        BookResponse response = new BookResponse();
        response.setBookTitle(book.getTitle());
        response.setBookIsbn(book.getIsbn());
        response.setBorrowerId(user.getId());
        response.setBorrowerName(user.getUsername());
        response.setBookId(book.getId());
        response.setAvailable(book.isAvailable());
        return response;
    }
    public static User map(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setPassword(registerUserRequest.getPassword());
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
    }
    private static void validateRequest(RegisterAdminRequest registerAdminRequest) {
        if(!registerAdminRequest.getUsername().matches("[a-zA-Z0-9]+"))throw new InvalidUserNameException("Username Can Only Contain Alphabet And Number and not null");
        if(registerAdminRequest.getPassword().trim().isEmpty())throw new InvalidPasswordException("Provide A Valid Password");
    }

}
