package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entites.Patient;
import com.demo.spring.entites.PatientDTO;
import com.demo.spring.exception.PatientAlredyExistsException;
import com.demo.spring.exception.PatientNotFoundException;
import com.demo.spring.repository.PatientRepository;
import com.demo.spring.util.Message;

@Service
public class PatientService {
	
	@Autowired
	PatientRepository patientRepository;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public Patient findPatientById(Integer id) throws PatientNotFoundException  {
		Optional<Patient> patiOps = patientRepository.findById(id);
		if(patiOps.isPresent()) {
			logger.info("Find Patient Successfully");
			return patiOps.get();
		}else {
			logger.error("Exception:Patient Not Found");
			throw new PatientNotFoundException();
		}
	}
	
	public Message register(PatientDTO patientDTO) throws PatientAlredyExistsException {
		if(patientRepository.existsByEmail(patientDTO.getEmail())) {
			logger.error("Exception:Patient Alredy Exists");
			throw new PatientAlredyExistsException();
		}else {
			Patient patient = new Patient(patientDTO.getPatientId(), patientDTO.getfirstName(), 
					patientDTO.getlastName(), patientDTO.getEmail());
			 patientRepository.save(patient);
			 logger.info("Register Patient Successfully");
			 return new Message("Patient is registered");
		}
	}

	public List<Patient> findAll(String fname) {
		logger.info("List Patient Successfully");
		return patientRepository.findAllByName(fname);
	}
	
	public Message updateRecord(PatientDTO patientDTO) throws PatientNotFoundException{
		if(patientRepository.existsById(patientDTO.getPatientId())) {
			patientRepository.update(patientDTO.getPatientId(), patientDTO.getfirstName(), patientDTO.getlastName(), patientDTO.getEmail());
			logger.info("Update Patient Successfully");
			return new Message("Patient Record is Updated");
		}else {
			logger.error("Exception:Patient Not Found");
			throw new PatientNotFoundException();
		}
	}
}
