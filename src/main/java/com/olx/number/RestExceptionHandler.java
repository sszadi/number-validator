package com.olx.number;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@EnableWebMvc
class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(FileParseException.class)
	protected ResponseEntity<Object> handleFileParseException(EntityNotFoundException ex) {
		return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileNotFoundException.class)
	protected ResponseEntity<Object> handleFileNotFound(EntityNotFoundException ex) {
		return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
	}
}
