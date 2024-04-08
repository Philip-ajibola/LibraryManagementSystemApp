package org.example.exception;

public class InvalidISBNNumberException extends LibraryManagementSystemException{
    public InvalidISBNNumberException(String message) {
        super(message);
    }
}
