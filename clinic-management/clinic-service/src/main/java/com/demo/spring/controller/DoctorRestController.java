package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Doctor;
import com.demo.spring.exceptions.DoctorNotFoundException;
import com.demo.spring.services.DoctorService;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/doctor")
public class DoctorRestController {
	
	@Autowired
	DoctorService doctorService;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@GetMapping(path="/list",produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.doctor.list")
	public ResponseEntity<List<Doctor>> listDoctorData() throws DoctorNotFoundException{
		List<Doctor> doctorList = doctorService.listAllData();
		if(doctorList.isEmpty()) {
			throw new DoctorNotFoundException();
		}else {
			logger.info("Call to service List All Doctor in clinic");
			return ResponseEntity.ok(doctorList);
		}
	}

}
