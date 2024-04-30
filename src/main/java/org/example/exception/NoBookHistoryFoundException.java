package org.example.exception;

public class NoBookHistoryFoundException extends LibraryManagementSystemException {
    public NoBookHistoryFoundException(String message) {
        super(message);
    }
}
