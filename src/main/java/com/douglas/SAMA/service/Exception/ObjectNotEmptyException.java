package com.douglas.SAMA.service.Exception;

import java.io.Serializable;

public class ObjectNotEmptyException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 1L;

	public ObjectNotEmptyException(String message, Throwable cause) {
		super(message, cause);

	}

	public ObjectNotEmptyException(String message) {
		super(message);

	}

}
