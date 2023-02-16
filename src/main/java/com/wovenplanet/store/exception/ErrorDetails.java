package com.wovenplanet.store.exception;

import java.util.Date;

public class ErrorDetails {
    private String timestamp;
    private String message;
    private String details;
	public ErrorDetails(String timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
}
