package com.douglas.SAMC.service.Exception;

public class WithoutAuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WithoutAuthorizationException(String message, Throwable cause) {
		super(message, cause);

	}

	public WithoutAuthorizationException(String message) {
		super(message);

	}

}
