package com.periodicals.exceptions;

public class TransactionException extends Throwable {

    public TransactionException() {

    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }
}
