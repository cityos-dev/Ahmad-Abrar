package com.wovenplanet.store.exception;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileStoreExceptionHandler {
	
	@ExceptionHandler(InvalidLocationException.class)
	public ResponseEntity<?> invalidLocationExceptionHandling(Exception ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorDetails(Instant.now().toString(), ex.getMessage(), request.getDescription(false)));
	}
    
    @ExceptionHandler(FileStorageException.class)
	public ResponseEntity<?> fileStorageExceptionHandling(Exception ex, WebRequest request) {
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorDetails(Instant.now().toString(), ex.getMessage(), request.getDescription(false)));
	}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> globalExceptionHandling(Exception ex, WebRequest request) {
    	if (ex instanceof NullPointerException || ex instanceof MultipartException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
