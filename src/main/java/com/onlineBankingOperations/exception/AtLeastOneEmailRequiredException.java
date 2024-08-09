package com.onlineBankingOperations.exception;

public class AtLeastOneEmailRequiredException extends RuntimeException{
    public AtLeastOneEmailRequiredException(String message) {
        super(message);
    }
}
