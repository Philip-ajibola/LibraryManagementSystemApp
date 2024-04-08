package org.example.exception;

public class UserNotFoundException extends LibraryManagementSystemException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
