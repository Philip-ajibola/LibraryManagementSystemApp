package org.example.controller;

import org.example.dto.request.*;
import org.example.dto.response.ApiResponse;
import org.example.exception.LibraryManagementSystemException;
import org.example.services.LibraryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Api/V1")
public class LibrarianController {
    @Autowired
    private LibraryServices libraryServices;
    @PostMapping("/create_librarian")
    public ResponseEntity<?> createAdmin(@RequestBody RegisterAdminRequest registerAdminRequest){
        try{
            var result = libraryServices.registerAdmin(registerAdminRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/add_book")
    public ResponseEntity<?> addBook(@RequestBody AddBookRequest bookRequest){
        try{
            var result = libraryServices.addBook(bookRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete_book")
    public ResponseEntity<?> deleteBook(@RequestBody DeleteBookRequest deleteBookRequest){
        try{
            var result = libraryServices.deleteBook(deleteBookRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get_transactions/{username}")
    public ResponseEntity<?> getTransactions(@PathVariable("username") String username){
        try{
            var result = libraryServices.getTransactionHistory(username);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/available_books/{username}")
    public ResponseEntity<?> getAvailableBooks(@PathVariable("username") String username){
        try{
            var result = libraryServices.getAvailablebooks(username);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/borrowed_book/{username}")
    public ResponseEntity<?> getBorrowedBook(@PathVariable("username") String username){
        try{
            var result = libraryServices.getBorrowedBook(username);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/reset_librarian")
    public ResponseEntity<?> resetAdmin(@RequestBody ResetAdminRequest resetAdminRequest){
        var result = libraryServices.resetAdmin(resetAdminRequest);
        return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequest logInRequest){
        try{
            var result = libraryServices.login(logInRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogOutRequest logoutRequest){
        try{
            var result = libraryServices.logout(logoutRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get_book_history")
    public ResponseEntity<?> getBookHistory(@RequestBody GetBookHistoryRequest getBookHistoryRequest ){
        try{
            var result = libraryServices.getHistoryOfBook(getBookHistoryRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch (LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }




}
