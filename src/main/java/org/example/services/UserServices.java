package org.example.services;

import org.example.dto.request.*;
import org.example.dto.response.*;

import java.util.List;

public interface UserServices {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);

    String deleteUser(DeleteUserRequest deleteUserRequest);

    BorrowBookResponse requestBook(BorrowBookRequest borrowBookRequest);

    ReturnBookResponse returnBook(ReturnBookRequest returnBookRequest);

    String login(LogInRequest logInRequest);
    String logOut(LogOutRequest logOutRequest);
    List<BorrowBookResponseForUser> getBorrowedBook(String username);
    List<AvailableBookResponse> viewAvailableBook(String username);
    List<AddBookResponse> findBookByCategory(FindByBookCategoryRequest request);
    List<AddBookResponse> findBookByAuthor(FindBookByAuthorReQuest request);
}
