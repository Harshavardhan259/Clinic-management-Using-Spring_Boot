package com.demo.spring.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.spring.utiles.Message;

@ControllerAdvice
public class PatientNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<Message> handlePatientNotFound(PatientNotFoundException ex){
		return ResponseEntity.ok(new Message("Patient Not Registered"));
	}
	
}