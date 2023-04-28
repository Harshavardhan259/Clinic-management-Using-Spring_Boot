package com.demo.spring.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.spring.utiles.Message;

@ControllerAdvice
public class DoctoreAppointmentNotFundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler(DoctoreAppointmentNotFundException.class)
	public ResponseEntity<Message> handleDoctoreAppointmentNotFoun(DoctoreAppointmentNotFundException ex){
		return ResponseEntity.ok(new Message("Apoointment not Found for Doctor"));
	}


}
