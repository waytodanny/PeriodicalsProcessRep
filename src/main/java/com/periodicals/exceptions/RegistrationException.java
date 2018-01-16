package com.periodicals.exceptions;

public class RegistrationException extends Throwable {
    public RegistrationException() {

    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(Throwable cause) {
        super(cause);
    }
}
