package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Appointment;
import com.demo.spring.entity.AppointmentDTO;
import com.demo.spring.exceptions.DoctoreAppointmentNotFundException;
import com.demo.spring.exceptions.PatientAppointmentNotFundException;
import com.demo.spring.exceptions.PatientNotFoundException;
import com.demo.spring.service.AppointmentService;
import com.demo.spring.utiles.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/appointment")
public class AppointmentRestController {


	@Autowired
	AppointmentService appservice;

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@PostMapping(path="/addVisit" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.appointment.addVisit")
	public ResponseEntity<Message> addVisits(@RequestBody AppointmentDTO appointmentDTO) 
			throws PatientNotFoundException{
		logger.info("Call to service AddVisit for adding appointment for patient ");
		return ResponseEntity.ok(appservice.addVisit(appointmentDTO));
	}
	
	@GetMapping(path="/listPatient/{patientId}" , produces = MediaType.APPLICATION_JSON_VALUE )
	@Timed(value="request.appointment.listPatient")
	public ResponseEntity<List<Appointment>> findAllforPatient(@PathVariable("patientId") int id) throws PatientAppointmentNotFundException{
		List<Appointment> appList = appservice.listAllForPatient(id);
		if(appList.isEmpty()) {
			logger.error("Exception:Patient Not found");
			throw new  PatientAppointmentNotFundException();
		}else {
			logger.info("Call to service list Appointment for Patient");
			return ResponseEntity.ok(appList);
		}
	}
	
	@GetMapping(path="/listDoctore" , produces = MediaType.APPLICATION_JSON_VALUE )
	@Timed(value="request.appointment.listDoctor")
	public ResponseEntity<List<Appointment>> findAllforDoctore(@RequestParam String date) throws DoctoreAppointmentNotFundException{
		List<Appointment> appList = appservice.listAllForDoctore(date);
		if(appList.isEmpty()) {
			logger.error("Exception:Doctor Not found");
			throw new DoctoreAppointmentNotFundException();
		}else {
			logger.info("Call to service list Appointment for Doctor");
			return ResponseEntity.ok(appList);
		}
	}

	
	

}
