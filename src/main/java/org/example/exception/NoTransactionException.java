package org.example.exception;

public class NoTransactionException extends LibraryManagementSystemException{
    public NoTransactionException(String message) {
        super(message);
    }
}
