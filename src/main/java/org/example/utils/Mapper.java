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
import java.time.LocalDateTime;

public class Mapper {
    private static String otp;
    private static RegisterUserRequest request = new RegisterUserRequest();
    private static LocalDateTime OTPtimeRange = LocalDateTime.now();


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

    public static BorrowBookResponseForUser mapUserBorrowedBook(Book book) {
        BorrowBookResponseForUser borrowedBook = new BorrowBookResponseForUser();
        borrowedBook.setBookId(book.getId());
        borrowedBook.setBookTitle(book.getTitle());
        borrowedBook.setBookAuthor(book.getAuthorName());
        borrowedBook.setCategory(book.getBookCategory());
        return borrowedBook;
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
    public static ReturnBookResponse mapp( Book book) {
        ReturnBookResponse response = new ReturnBookResponse();
        response.setBookTitle(book.getTitle());
        response.setBookIsbn(book.getIsbn());
        response.setBookId(book.getId());
        response.setReturnedDate(LocalDate.now());
        return response;
    }
    public static User map(RegisterUserRequest registerUserRequest) {
        request = registerUserRequest;
        User user = new User();
        StrongPasswordEncryptor passwordEncryptor =  new StrongPasswordEncryptor();
        user.setPassword(passwordEncryptor.encryptPassword(registerUserRequest.getPassword()));
        user.setUsername(registerUserRequest.getUsername().toLowerCase());
        user.setEmail(registerUserRequest.getEmail());
        EmailSender emailSender = new EmailSender();
        emailSender.setSubject("Welcome to Magnificent Online Book Store! YOUR OTP");
        emailSender.setReceiverMail(registerUserRequest.getEmail());
        emailSender.setMessage(sendMessage(registerUserRequest.getUsername(), user.getEmail()));
        emailSender.send();
        return user;
    }

    private static String sendMessage(String username,String email) {
        return String.format("""
                                
                Dear %s,
                                
                Congratulations! YouWelcome to Magnificent Online Book Store! have successfully registered on Magnificent Online Book Store!.
               We are excited to have you as a part of our community.
                                
                Your account details are as follows:            
                Username: %s
                Email: %s
                
                YOUR OTP : 123456
                
                You can now log in to your account and start using our services. If you have any
               questions or concerns, please don't hesitate to contact us at "ajibolaphilip10@gmail.com".
                                
                Thank you for choosing Magnificent Online Book Store!. We look forward to serving you.
                                
                Best regards,
                                
                                                                         Magnificent Online Book Store!
                                          
                """,username,username,email);
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
