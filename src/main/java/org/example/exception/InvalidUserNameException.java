package org.example.exception;

public class InvalidUserNameException extends LibraryManagementSystemException{
    public InvalidUserNameException(String message) {
        super(message);
    }
}
