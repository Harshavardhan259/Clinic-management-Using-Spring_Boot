package com.demo.spring.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.spring.utiles.Message;

@ControllerAdvice
public class PatientAppointmentNotFundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(PatientAppointmentNotFundException.class)
	public ResponseEntity<Message> handlePatientAppointmentNotFund(PatientAppointmentNotFundException ex){
		return ResponseEntity.ok(new Message("Appointment for Patient Not fund"));
	}
}
