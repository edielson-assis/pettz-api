package br.com.pettz.security.exception;

public class SecurityException extends RuntimeException {
    
    public SecurityException(String msg) {
        super(msg);
    }

    public SecurityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}