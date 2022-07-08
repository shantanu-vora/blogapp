package com.shantanu.blogapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass {

	@ExceptionHandler
	public ResponseEntity<CustomErrorResponse> handleException(CustomException e) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse();
		customErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		customErrorResponse.setMessage(e.getMessage());
		customErrorResponse.setTime(System.currentTimeMillis());

		return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
	}
}
