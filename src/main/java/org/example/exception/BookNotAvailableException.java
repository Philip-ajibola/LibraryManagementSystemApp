package org.example.exception;

public class BookNotAvailableException extends LibraryManagementSystemException {
    public BookNotAvailableException(String s) {
        super(s);
    }
}
