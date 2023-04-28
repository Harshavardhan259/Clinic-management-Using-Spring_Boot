package com.demo.spring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.spring.util.Message;

@ControllerAdvice
public class PatientNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<Message> patientNotFund(PatientNotFoundException ex){
		return ResponseEntity.ok(new Message("patient Not Fund"));
	}

}
