package com.wovenplanet.store.exception;

public class FileConflictException extends RuntimeException {
	public FileConflictException() {
        super();
    } 
	
	public FileConflictException(String message) {
        super(message);
    }
}
