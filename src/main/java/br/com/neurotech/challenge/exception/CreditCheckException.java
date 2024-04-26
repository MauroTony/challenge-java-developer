package br.com.neurotech.challenge.exception;

public class CreditCheckException extends RuntimeException {
    public CreditCheckException(String message) {
        super(message);
    }
}