package org.example.exception;

public class InvalidPasswordException extends LibraryManagementSystemException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
