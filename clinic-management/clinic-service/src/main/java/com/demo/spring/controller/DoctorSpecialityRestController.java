package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Doctor;
import com.demo.spring.entity.DoctorSpecialityDTO;
import com.demo.spring.exceptions.DoctorNotFoundException;
import com.demo.spring.exceptions.DoctoreSpecialityNotFoundException;
import com.demo.spring.services.DoctorSpecialityService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/speciality")
public class DoctorSpecialityRestController {

	@Autowired
	DoctorSpecialityService doctorSpecialityService;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());

	@GetMapping(path = "/list/{specialityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.doctorSpeciality.list")
	public ResponseEntity<List<Doctor>> listAllDoctor(@PathVariable("specialityId") Integer id) throws DoctoreSpecialityNotFoundException, DoctorNotFoundException {
		List<Doctor> doctorList =doctorSpecialityService.listAllSpeciality(id);
		if(doctorList.isEmpty()) {
			throw new DoctoreSpecialityNotFoundException();
		}else {
			logger.info("Call to service List All Doctor in speciality");
			return ResponseEntity.ok(doctorList);
		}
		
	}
	
	@PostMapping(path="/addDoctor" ,consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.doctorSpeciality.addDoctor")
	public ResponseEntity<Message> addDoctorTOSpeciality(@RequestBody DoctorSpecialityDTO dto) throws DoctorNotFoundException{
		logger.info("Call to service Add Doctor to speciality");
		return ResponseEntity.ok(doctorSpecialityService.addDoctor(dto));
	}
	
	@DeleteMapping(path = "/removeDoctor/{doctorId}/{specialityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.doctorSpeciality.removeDoctor")
	public ResponseEntity<Message> removeDoctor(@PathVariable("doctorId") Integer id, @PathVariable("specialityId") Integer specialityId) throws DoctorNotFoundException{
		logger.info("Call to service Remove Doctor From speciality");
		return ResponseEntity.ok(doctorSpecialityService.removeDoctor(id,specialityId));
	}

}
