package org.example.services;

import org.example.data.model.Librarian;
import org.example.data.model.Book;
import org.example.data.model.History;
import org.example.data.repository.LibrarianRepository;
import org.example.dto.request.*;
import org.example.dto.response.AddBookResponse;
import org.example.dto.response.AvailableBookResponse;
import org.example.dto.response.BorrowedBookResponse;
import org.example.dto.response.RegisterAdminResponse;
import org.example.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static org.example.utils.Mapper.*;

@Service
public class LibrarianServiceImpl implements LibraryServices {
    @Autowired
    private TransactionServices transactions;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private BookServices bookServices;
    @Override
    public RegisterAdminResponse registerAdmin(RegisterAdminRequest registerAdminRequest) {
        validateRequest(registerAdminRequest);
       if(librarianRepository.count()==1)throw new LibrarianExistException("Admin Already Exist");
       Librarian librarian = librarianRepository.save(map(registerAdminRequest));
        return map(librarian);
    }


    @Override
    public void addTransaction(History history, String username) {
        Book book = bookServices.findBookById(history.getBookId());
        validateAdmin(username.toLowerCase());
        validateLogin(username.toLowerCase());
        transactions.save(history);
    }

    private void validateAdmin(String username) {
        if(username.isEmpty())throw new InvalidUserNameException("Provide A valid UserName");
        Librarian admin = librarianRepository.findByUsername(username.toLowerCase());
        if(admin == null)throw new UserNotFoundException("Admin Not Found");
    }

    @Override
    public RegisterAdminResponse resetAdmin(ResetAdminRequest resetAdminRequest) {
        Librarian admin = librarianRepository.findByUsername(resetAdminRequest.getOldUsername().toLowerCase());
        if(admin == null) throw new LibraryManagementSystemException("Admin Not Found");
        if(!resetAdminRequest.getOldPassword().equals(admin.getPassword()))throw new InvalidPasswordException("InValid Password");
        admin.setUsername(resetAdminRequest.getNewUsername().toLowerCase());
        admin.setPassword(resetAdminRequest.getNewPassword());
        librarianRepository.save(admin);
        return map(admin);
    }


    @Override
    public AddBookResponse addBook(AddBookRequest bookRequest) {
        validateAdmin(bookRequest.getAdminName().toLowerCase());
        validateLogin(bookRequest.getAdminName().toLowerCase());
        return bookServices.addbook(bookRequest);
    }
    @Override
    public String  deleteBook(DeleteBookRequest deleteBookRequest) {
        validateAdmin(deleteBookRequest.getAdminName().toLowerCase());
        validateLogin(deleteBookRequest.getAdminName().toLowerCase());
        Book book = bookServices.findBookByIsbn(deleteBookRequest.getIsbn());
        if(book == null)throw new BookNotFoundException("Book not Found");
        if(!book.isAvailable())throw new BookNotAvailableException("Book Has Been Borrowed And Can't Be Deleted");
        return bookServices.deleteBook(deleteBookRequest);
    }

    @Override
    public String login(LogInRequest logInRequest) {
        validateAdmin(logInRequest.getUsername().toLowerCase());
        Librarian admin = librarianRepository.findByUsername(logInRequest.getUsername().toLowerCase());
        if(!logInRequest.getPassword().equals(admin.getPassword()))throw new InvalidPasswordException("Wrong password");
        admin.setLoggedIn(true);
        librarianRepository.save(admin);
        return "Login successful";
    }
    private static void validateRequest(RegisterAdminRequest registerAdminRequest) {
        if(!registerAdminRequest.getUsername().matches("[a-zA-Z0-9]+"))throw new InvalidUserNameException("Username Can Only Contain Alphabet And Number and not null");
        if(registerAdminRequest.getPassword().trim().isEmpty())throw new InvalidPasswordException("Provide A Valid Password");
    }


    @Override
    public String logout(LogOutRequest logOutRequest) {
        Librarian admin = librarianRepository.findByUsername(logOutRequest.getUsername().toLowerCase());
        admin.setLoggedIn(false);
        librarianRepository.save(admin);
        return "logout SuccessFul";
    }
    private void validateLogin(String username){
        Librarian admin = librarianRepository.findByUsername(username.toLowerCase());
        validateAdmin(username);
        if(!admin.isLoggedIn())throw new LoginException("Admin is not loggedIn");
    }

    @Override
    public List<History> getBooksHistory(String username) {
        validateAdmin(username.toLowerCase());
        if(transactions.findAll().isEmpty()) throw new NoTransactionException("No Transaction Made Yet");
        return transactions.findAll();
    }


    @Override
    public List<AvailableBookResponse> getAvailablebooks(String username) {
       validateAdmin(username.toLowerCase());
       List<AvailableBookResponse> availableBookResponses = new ArrayList<>();
       for(Book book: bookServices.findAll()){
           if(book.isAvailable()) availableBookResponses.add(mapAvailableBookResponse(book));
       }
       if(availableBookResponses.isEmpty())throw new NoTransactionException("No Book Available");
       return availableBookResponses;
    }

    @Override
    public List<BorrowedBookResponse> getBorrowedBook(String username) {
        validateAdmin(username.toLowerCase());
        List<BorrowedBookResponse> borrowedBookResponse = new ArrayList<>();
        for(Book book: bookServices.findAll()) borrowedBookResponse.add(mapBorrowedBookResponse(book));
        if(borrowedBookResponse.isEmpty())throw new NoTransactionException("No Book Available");
        return borrowedBookResponse;

    }



    @Override
    public List<History> getHistoryOfBook(GetBookHistoryRequest getBookHistoryRequest) {
        validateAdmin(getBookHistoryRequest.getLibrarianName());
        Book book = bookServices.findBookByIsbn(getBookHistoryRequest.getIsbn());
        List<History> bookHistory = new ArrayList<>();
        getBooksHistory(getBookHistoryRequest.getLibrarianName()).forEach(history ->{ if(history.getBookId().equals(book.getId())) bookHistory.add(history);});
        if(bookHistory.isEmpty())throw new NoBookHistoryFoundException(String.format("No History Of %s Found " ,book.getTitle()));
        return bookHistory;
    }


}
