package com.demo.spring.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.spring.util.Message;

@ControllerAdvice
public class DiagnosticNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler(DiagnosticNotFoundException.class)
	public ResponseEntity<Message> handleNotFound(DiagnosticNotFoundException ex){
		return ResponseEntity.ok(new Message("Diagnostic Not Found"));
	}

}
