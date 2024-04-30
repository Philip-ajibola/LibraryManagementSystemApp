package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.History;
import org.example.dto.request.*;
import org.example.dto.response.AddBookResponse;
import org.example.dto.response.AvailableBookResponse;
import org.example.dto.response.BorrowedBookResponse;
import org.example.dto.response.RegisterAdminResponse;

import java.util.List;

public interface LibraryServices {
    RegisterAdminResponse registerAdmin(RegisterAdminRequest registerAdminRequest);

    void addTransaction(History history, String username);
    RegisterAdminResponse resetAdmin(ResetAdminRequest deleteAdminRequest);


    AddBookResponse addBook(AddBookRequest bookRequest);

    String deleteBook(DeleteBookRequest deleteBookRequest);

    String login(LogInRequest logInRequest);

    String logout(LogOutRequest logOutRequest);

    List<History> getTransactionHistory(String username);
    List<AvailableBookResponse> getAvailablebooks(String username);

    List<BorrowedBookResponse> getBorrowedBook(String username);
    List<History> getHistoryOfBook(GetBookHistoryRequest getBookHistoryRequest);

}
