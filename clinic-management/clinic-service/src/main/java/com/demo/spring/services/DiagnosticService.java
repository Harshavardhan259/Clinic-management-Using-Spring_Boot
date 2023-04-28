package com.demo.spring.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.entity.Diagnostic;
import com.demo.spring.entity.DiagnosticDTO;
import com.demo.spring.entity.PatientDTO;
import com.demo.spring.exceptions.DiagnosticNotFoundException;
import com.demo.spring.exceptions.DiagnosticTestAlredyExistsException;
import com.demo.spring.exceptions.PatientNotFoundException;
import com.demo.spring.repository.DiagnosticRepository;
import com.demo.spring.repository.PatientDiagnosticRepository;
import com.demo.spring.util.Message;

@Service
public class DiagnosticService {
	
	@Autowired
	DiagnosticRepository diagnosticRepository;
	
	@Autowired
	PatientDiagnosticRepository patientDiagnosticRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public List<Diagnostic> listAll(){
		logger.info("List All Diagnostic in Clinic");
		return diagnosticRepository.findAll();
	}
	
	public Message addTests(DiagnosticDTO diagnosticDTO) throws DiagnosticTestAlredyExistsException {
		if(diagnosticRepository.existsByDiagnosticName(diagnosticDTO.getDiagnosticName())) {
			logger.error("Exception:Diagnostic test is alredy exists");
			throw new DiagnosticTestAlredyExistsException();
		}else {
			Diagnostic diagnostic = new Diagnostic(diagnosticDTO.getDiagnosticId(), diagnosticDTO.getDiagnosticName());
			diagnosticRepository.save(diagnostic);
			logger.info("Add Diagnostic Test to Diagnostic List");
			return new Message("Test Added");
		}
	}
	
	public Message removeTests(Integer id) throws DiagnosticNotFoundException {
		Optional<Diagnostic> diagnostic = diagnosticRepository.findById(id);
		if(diagnostic.isPresent()) {
			diagnosticRepository.delete(diagnostic.get());
			logger.info("Remove Diagnostic Test From Diagnostic List");
			return new Message("Test Removed From test List");
		}else {
			logger.error("Exception:Diagnostic test is Not Found");
			throw new DiagnosticNotFoundException();
		}
	}
	
	
	public Message addDiagnosticTest(DiagnosticDTO dto, Integer patientId) throws DiagnosticNotFoundException , PatientNotFoundException{
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> req = new HttpEntity<>(headers);

		
			
			ResponseEntity<PatientDTO> responce1 = restTemplate.exchange("http://patient-service/patient/"+patientId,
					HttpMethod.GET, req, PatientDTO.class);
			PatientDTO patientDTO  = responce1.getBody();
			if(patientDTO!=null && patientDTO.getPatientId()!=null && patientDTO.getPatientId().equals(patientId)) {
				if(diagnosticRepository.existsById(dto.getDiagnosticId())) {
					patientDiagnosticRepository.addDiagnosticToPatient(dto.getDiagnosticId(),patientId);
					logger.info("Add Diagnostic Test to Patient");
					return new Message("Diagnostic test is added to Patient");
				}else {
					logger.error("Exception:Diagnostic test Not Found");
					throw new DiagnosticNotFoundException();
				}
		
			}else {
				logger.error("Exception:Patient Not Registered");
				throw new PatientNotFoundException();
			}
				
	}

}
