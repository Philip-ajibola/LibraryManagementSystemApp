package org.example.exception;

public class BookNotFoundException extends LibraryManagementSystemException{
    public BookNotFoundException(String message) {
        super(message);
    }
}
