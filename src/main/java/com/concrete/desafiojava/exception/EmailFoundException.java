package com.concrete.desafiojava.exception;

public class EmailFoundException extends Exception {

	private static final long serialVersionUID = -3095530127254132164L;

	public EmailFoundException() {
		super();
	}

	public EmailFoundException(String message) {
		super(message);
	}

	public EmailFoundException(Throwable cause) {
		super(cause);
	}

	public EmailFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
