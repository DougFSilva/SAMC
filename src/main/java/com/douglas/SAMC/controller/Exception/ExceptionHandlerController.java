package com.douglas.SAMC.controller.Exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.douglas.SAMC.service.Exception.DataIntegratyViolationException;
import com.douglas.SAMC.service.Exception.ObjectNotEmptyException;
import com.douglas.SAMC.service.Exception.ObjectNotFoundException;
import com.douglas.SAMC.service.Exception.OperationNotAllowedException;
import com.douglas.SAMC.service.Exception.UploadFailedException;
import com.douglas.SAMC.service.Exception.WithoutAuthorizationException;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFoundException(ObjectNotFoundException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandartError> DataIntegratyViolationException(DataIntegratyViolationException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandartError> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(WithoutAuthorizationException.class)
	public ResponseEntity<StandartError> WithoutAuthorizationException(WithoutAuthorizationException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<StandartError> SQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<StandartError> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(ObjectNotEmptyException.class)
	public ResponseEntity<StandartError> ObjectNotEmptyException(ObjectNotEmptyException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(OperationNotAllowedException.class)
	public ResponseEntity<StandartError> OperationNotAllowedException(OperationNotAllowedException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(UploadFailedException.class)
	public ResponseEntity<StandartError> UploadFailedException(UploadFailedException e) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}


}
