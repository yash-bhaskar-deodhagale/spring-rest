package com.yash.org.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yash.org.model.EmployeeErrorResponse;

@ControllerAdvice
public class EmployeeExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> handleException(EmployeeNotFoundException empNotFoundException){
		EmployeeErrorResponse error=new EmployeeErrorResponse(HttpStatus.NOT_FOUND.value(),
										empNotFoundException.getMessage(),
										System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> handleException(Exception exception){
		EmployeeErrorResponse error=new EmployeeErrorResponse(HttpStatus.BAD_REQUEST.value(),
				exception.getMessage(),
				System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

}
