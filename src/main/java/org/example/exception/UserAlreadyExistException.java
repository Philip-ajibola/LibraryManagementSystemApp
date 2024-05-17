package org.example.exception;

public class UserAlreadyExistException extends LibraryManagementSystemException {
    public UserAlreadyExistException(String userAlreadyExist) {
        super(userAlreadyExist);
    }
}
