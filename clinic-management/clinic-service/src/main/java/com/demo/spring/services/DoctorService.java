package com.demo.spring.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Doctor;
import com.demo.spring.repository.DoctorRepository;

@Service
public class DoctorService {

	
	@Autowired
	DoctorRepository doctorRepository;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public List<Doctor> listAllData(){
		logger.info("List All Doctor in Clinic");
		return doctorRepository.findAll();
	}
}
