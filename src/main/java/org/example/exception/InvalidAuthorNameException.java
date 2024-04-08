package org.example.exception;

public class InvalidAuthorNameException extends LibraryManagementSystemException{
    public InvalidAuthorNameException(String message) {
        super(message);
    }
}
