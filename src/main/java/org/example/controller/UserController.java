package org.example.controller;

import org.example.dto.*;
import org.example.dto.response.ApiResponse;
import org.example.exception.LibraryManagementSystemException;
import org.example.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Api/V2")
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/register_user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        try{
            var result = userServices.registerUser(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch(LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LogInRequest logInRequest){
        try{
            var result = userServices.login(logInRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch(LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/logout")
    private ResponseEntity<?> logout(@RequestBody LogOutRequest logOutRequest){
        try{
            var result = userServices.logOut(logOutRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch(LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/BorrowedBook/{username}")
    public ResponseEntity<?> getListOfBorrowedBook(@PathVariable("username") String username){
        try{
            var result = userServices.getBorrowedBook(username);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch(LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/request_book")
    public ResponseEntity<?> requestBook(@RequestBody BorrowBookRequest bookRequest){
        try{
            var result = userServices.requestBook(bookRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch(LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/return_book")
    public ResponseEntity<?> returnBook(@RequestBody ReturnBookRequest bookRequest){
        try{
            var result = userServices.returnBook(bookRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch(LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete_user")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest){
        try{
            var result = userServices.deleteUser(deleteUserRequest);
            return new ResponseEntity<>(new ApiResponse(true,result), HttpStatus.CREATED);
        }catch(LibraryManagementSystemException e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }




}
