package org.example.exception;

public class InvalidEmailException extends LibraryManagementSystemException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
