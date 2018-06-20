package com.concrete.desafiojava.exception;

public class SessionException extends Exception {

	private static final long serialVersionUID = 2969152902190725414L;

	public SessionException() {
	}

	public SessionException(String message) {
		super(message);
	}

	public SessionException(Throwable cause) {
		super(cause);
	}

	public SessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
