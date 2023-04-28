package com.demo.spring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.spring.util.Message;

@ControllerAdvice
public class UseNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(UseNotFoundException.class)
	public ResponseEntity<Message> handleUseNotFound(UseNotFoundException ex){
		return ResponseEntity.ok(new Message("invalid username and password"));
	}
}
