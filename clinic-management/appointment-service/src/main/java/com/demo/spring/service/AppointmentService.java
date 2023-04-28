package com.demo.spring.service;

import java.util.List;

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

import com.demo.spring.entity.Appointment;
import com.demo.spring.entity.AppointmentDTO;
import com.demo.spring.entity.PatientDTO;
import com.demo.spring.exceptions.PatientNotFoundException;
import com.demo.spring.repository.AppointmentRepository;
import com.demo.spring.utiles.Message;

@Service
public class AppointmentService {

	@Autowired
	AppointmentRepository  apprepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public Message addVisit(AppointmentDTO appointmentDTO) throws PatientNotFoundException {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> req = new HttpEntity<>(headers);

		
			
			ResponseEntity<PatientDTO> responce1 = restTemplate.exchange("http://patient-service/patient/" +appointmentDTO.getPatientId(),
					HttpMethod.GET, req, PatientDTO.class);
		
			PatientDTO patientDTO = responce1.getBody();
			if( patientDTO!=null && patientDTO.getPatientId()!=null && patientDTO.getPatientId().equals(appointmentDTO.getPatientId())) {

					Appointment appointment = new Appointment(appointmentDTO.getAppointmentId(), appointmentDTO.getDoctorId(), appointmentDTO.getPatientId(), appointmentDTO.getDate());
					apprepository.save(appointment);
					logger.info("Appointment is Added Successfully");
					return new Message("Appointment is Added");
				
				
			}else {
				logger.error("Patient Not Registered");
				throw new PatientNotFoundException();
			}
		
	}
	
	public List<Appointment> listAllForPatient(Integer patientId){
		logger.info("list All Appointment for patient");
		return apprepository.findAllByPatientId(patientId);
	}
	
	public List<Appointment> listAllForDoctore(String date){
		logger.info("list All Appointment for doctor");
		return apprepository.findByDate(date);
	}
}
