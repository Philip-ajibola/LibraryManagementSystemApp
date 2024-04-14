package org.example.exception;

public class OverDueBalanceException extends LibraryManagementSystemException {
    public OverDueBalanceException(String message) {
        super(message);
    }
}
