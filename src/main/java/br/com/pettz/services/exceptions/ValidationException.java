package br.com.pettz.services.exceptions;

public class ValidationException extends RuntimeException {
    
    public ValidationException(String msg) {
        super(msg);
    }
}