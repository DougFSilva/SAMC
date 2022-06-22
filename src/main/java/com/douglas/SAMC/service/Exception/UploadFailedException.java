package com.douglas.SAMC.service.Exception;

public class UploadFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UploadFailedException(String message, Throwable cause) {
		super(message, cause);

	}

	public UploadFailedException(String message) {
		super(message);

	}

}
