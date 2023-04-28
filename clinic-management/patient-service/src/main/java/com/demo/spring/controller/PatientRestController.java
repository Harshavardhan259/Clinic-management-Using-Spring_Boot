package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entites.Patient;
import com.demo.spring.entites.PatientDTO;
import com.demo.spring.exception.PatientAlredyExistsException;
import com.demo.spring.exception.PatientNotFoundException;
import com.demo.spring.service.PatientService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/patient")
public class PatientRestController {
	
	@Autowired
	PatientService patientService;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@GetMapping(path="/{patientId}" ,produces="application/json" )
	@Timed(value="request.patient.find")
	public ResponseEntity<Patient> findPatient(@PathVariable("patientId") Integer id) throws PatientNotFoundException {
		logger.info("Call to service Find patient");
		return ResponseEntity.ok(patientService.findPatientById(id));
	}
	
	@PostMapping(path="/register" ,produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.patient.register")
	public ResponseEntity<Message> register(@RequestBody PatientDTO patientDto ) throws PatientAlredyExistsException{
		logger.info("Call to service register patient");
		return ResponseEntity.ok(patientService.register(patientDto));
	}
	
	@GetMapping(path="/list/{fname}" , produces = MediaType.APPLICATION_JSON_VALUE )
	@Timed(value="request.patient.list")
	public ResponseEntity<List<Patient>> listByPatientName(@PathVariable("fname") String fname) throws PatientNotFoundException{
		List<Patient> patientList = patientService.findAll(fname);
		if(patientList.isEmpty()) {
			throw new PatientNotFoundException();
		}else {
			logger.info("Call to service List patient");
			return ResponseEntity.ok(patientList);
		}
		
		
	}
	
	@PatchMapping(path="/update" ,consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.patient.update")
	public ResponseEntity<Message> updatePatienRecord(@RequestBody PatientDTO patientDTO) throws PatientNotFoundException{
		logger.info("Call to service Update patient");
		return ResponseEntity.ok(patientService.updateRecord(patientDTO));
	}

	
	
	

}
