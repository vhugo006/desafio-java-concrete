package com.concrete.desafiojava.exception;

public class InvalidPasswordEmailException extends Exception {

	private static final long serialVersionUID = 1749492149507750342L;

	public InvalidPasswordEmailException() {
	}

	public InvalidPasswordEmailException(String message) {
		super(message);
	}

	public InvalidPasswordEmailException(Throwable cause) {
		super(cause);
	}

	public InvalidPasswordEmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPasswordEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
