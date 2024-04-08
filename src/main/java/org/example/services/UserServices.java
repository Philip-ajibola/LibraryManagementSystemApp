package org.example.services;

import org.example.data.model.Book;
import org.example.dto.*;
import org.example.dto.response.BookResponse;
import org.example.dto.response.RegisterUserResponse;

import java.util.List;

public interface UserServices {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);

    String deleteUser(DeleteUserRequest deleteUserRequest);

    BookResponse requestBook(BorrowBookRequest borrowBookRequest);

    BookResponse returnBook(ReturnBookRequest returnBookRequest);

    String login(LogInRequest logInRequest);

    String logOut(LogOutRequest logOutRequest);
    List<Book> getBorrowedBook(String username);
}
