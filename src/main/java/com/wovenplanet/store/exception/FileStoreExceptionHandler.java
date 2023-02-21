package com.wovenplanet.store.exception;

import java.time.Instant;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileStoreExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(FileConflictException.class)
	public ResponseEntity<?> fileConflictExceptionHandling(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(Instant.now().toString(),
				messageSource.getMessage("error.conflict", null, Locale.getDefault()), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(FileNotSupportedException.class)
	public ResponseEntity<?> fileNotSupportedExceptionHandling(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(Instant.now().toString(),
				messageSource.getMessage("error.unsupported", null, Locale.getDefault()),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> fileNotFoundExceptionHandling(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(Instant.now().toString(),
				messageSource.getMessage("error.notfound", null, Locale.getDefault()), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<?> fileStorageExceptionHandling(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(Instant.now().toString(),
				messageSource.getMessage("error.upload", null, Locale.getDefault()), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> globalExceptionHandling(Exception ex, WebRequest request) {
		if (ex instanceof NullPointerException || ex instanceof MultipartException) {
			ErrorDetails errorDetails = new ErrorDetails(Instant.now().toString(),
					messageSource.getMessage("error.missing", null, Locale.getDefault()),
					request.getDescription(false));
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
		ErrorDetails errorDetails = new ErrorDetails(Instant.now().toString(),
				messageSource.getMessage("error.generic", null, Locale.getDefault()), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
