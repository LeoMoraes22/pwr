package com.example.pwr.exception;

import java.io.Serial;

public class DefaultException extends Exception {
	
	@Serial
	private static final long serialVersionUID = 1;
	
	public DefaultException(String message) {
		super(message);
	}

}
