package org.example.exception;

public class InvalidBookTitleException extends LibraryManagementSystemException{
    public InvalidBookTitleException(String message) {
        super(message);
    }
}
