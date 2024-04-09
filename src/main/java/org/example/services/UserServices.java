package org.example.services;

import org.example.data.model.Book;
import org.example.dto.request.*;
import org.example.dto.response.BorrowBookResponse;
import org.example.dto.response.RegisterUserResponse;
import org.example.dto.response.ReturnBookResponse;

import java.util.List;

public interface UserServices {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);

    String deleteUser(DeleteUserRequest deleteUserRequest);

    BorrowBookResponse requestBook(BorrowBookRequest borrowBookRequest);

    ReturnBookResponse returnBook(ReturnBookRequest returnBookRequest);

    String login(LogInRequest logInRequest);

    String logOut(LogOutRequest logOutRequest);
    List<Book> getBorrowedBook(String username);
    List<Book> viewAvailableBook(String username);
}
