package org.example.exception;

public class BookAlreadyExistException extends LibraryManagementSystemException{
    public BookAlreadyExistException(String message) {
        super(message);
    }
}
