package br.com.pettz.services.exceptions;

public class FileStorageException extends RuntimeException{
	
	public FileStorageException(String ex) {
		super(ex);
	}
	
	public FileStorageException(String ex, Throwable cause) {
		super(ex, cause);
	}
}