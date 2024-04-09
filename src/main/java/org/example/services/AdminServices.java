package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.Transaction;
import org.example.dto.request.*;
import org.example.dto.response.AddBookResponse;
import org.example.dto.response.RegisterAdminResponse;

import java.util.List;

public interface AdminServices {
    RegisterAdminResponse registerAdmin(RegisterAdminRequest registerAdminRequest);

    void addTransaction(Transaction transaction,String username);
    RegisterAdminResponse resetAdmin(ResetAdminRequest deleteAdminRequest);


    AddBookResponse addBook(AddBookRequest bookRequest);

    String deleteBook(DeleteBookRequest deleteBookRequest);

    String login(LogInRequest logInRequest);

    String logout(LogOutRequest logOutRequest);

    List<Transaction> getTransactionHistory(String username);
    List<Book> getAvailablebooks(String username);

    List<Book> getBorrowedBook(String username);


}
