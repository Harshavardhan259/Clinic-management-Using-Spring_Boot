package com.demo.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Doctor;
import com.demo.spring.entity.DoctorSpeciality;
import com.demo.spring.entity.DoctorSpecialityDTO;
import com.demo.spring.exceptions.DoctorNotFoundException;
import com.demo.spring.repository.DoctorRepository;
import com.demo.spring.repository.DoctorSpecialityRepository;
import com.demo.spring.repository.SpecialityRepository;
import com.demo.spring.util.Message;

@Service
public class DoctorSpecialityService {

	@Autowired
	DoctorSpecialityRepository doctorSpecialityRepository;

	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	SpecialityRepository specialityRepository;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Message addDoctor(DoctorSpecialityDTO doctorSpecialityDTO) throws DoctorNotFoundException {

		if (doctorRepository.existsById(doctorSpecialityDTO.getDoctorId()) && specialityRepository.existsById(doctorSpecialityDTO.getSpecialityId())) {
			Optional<DoctorSpeciality> doctorOps = doctorSpecialityRepository.findByDoctorIdandSpecialityId(doctorSpecialityDTO.getDoctorId(), doctorSpecialityDTO.getSpecialityId());
			
			if(doctorOps.isPresent()) {
				logger.info("Doctor with speciality Alredy there");
				return new Message("Doctor with speciality Alredy there");
			}else {
				doctorSpecialityRepository.addDoctorToSpeciality(doctorSpecialityDTO.getDoctorId(),
						doctorSpecialityDTO.getSpecialityId());
				logger.info("Doctor Added to Speciality Successfully");
				return new Message("Doctore Added to Speciality");
			}
			
			
		} else {
			logger.error("Exception:Doctor Not Found");
			throw new DoctorNotFoundException();
		}

	}

	public Message removeDoctor(Integer doctorId , Integer specialityId) throws DoctorNotFoundException{
		Optional<DoctorSpeciality> doctorOps = doctorSpecialityRepository.findByDoctorIdandSpecialityId(doctorId, specialityId);
		if (doctorOps.isPresent()) {
			doctorSpecialityRepository.deleteDoctor(doctorOps.get().getDoctorId(), doctorOps.get().getSpecialityId());
			logger.info("Doctor Removed Speciality Successfully");
			return new Message("Doctore Remove from Speciality");
			
		} else {
			logger.error("Exception:Doctor Not Found");
			throw new DoctorNotFoundException();
		}

	}

	public List<Doctor> listAllSpeciality(Integer specialityId) throws DoctorNotFoundException {
		List<DoctorSpeciality> doctorIdList = doctorSpecialityRepository.findAllDoctor(specialityId);
		Integer i = 0;
		List<Doctor> doctorList = new ArrayList<>();
		if(doctorIdList.isEmpty()) {
			logger.error("Exception:Doctor is Not Found");
			throw new DoctorNotFoundException();
			
		}else {
			for (doctorRepository.existsById(doctorIdList.get(i).getDoctorId()); i < doctorIdList.size(); i++) {

				Optional<Doctor> doctorOps = doctorRepository.findById(doctorIdList.get(i).getDoctorId());
				if (doctorOps.isPresent()) {

					doctorList.add(doctorOps.get());	
				}
			}
			logger.info("List All Doctor in Speciality");
			return doctorList;
			
		}
		
	}

}
