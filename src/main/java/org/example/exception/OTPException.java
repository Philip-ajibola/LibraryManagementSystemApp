package org.example.exception;

public class OTPException extends LibraryManagementSystemException {
    public OTPException(String otpTimeOut) {
        super(otpTimeOut);
    }
}
