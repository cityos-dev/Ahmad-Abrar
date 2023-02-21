package com.wovenplanet.store.exception;

public class FileNotSupportedException extends RuntimeException{
	
	public FileNotSupportedException() {
        super();
    }
	
	public FileNotSupportedException(String message) {
        super(message);
    }
}
