package br.com.pettz.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    
    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}