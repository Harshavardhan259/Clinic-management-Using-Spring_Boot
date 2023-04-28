package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Doctor;
import com.demo.spring.entity.DoctorSpeciality;
import com.demo.spring.repository.DoctorRepository;
import com.demo.spring.repository.DoctorSpecialityRepository;
import com.demo.spring.repository.SpecialityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DoctorSpecialityRestControllerTest {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	DoctorSpecialityRepository doctorSpecialityRepository;
	
	@MockBean
	DoctorRepository doctorRepository;
	
	@MockBean
	SpecialityRepository specialityRepository;
	
	DoctorSpeciality doctorSpeciality = new DoctorSpeciality(101, 1);
	
	@Test
	void testListAllDoctor() throws Exception {
		Doctor doctor = new Doctor(101, "harsh", "mane", "harshmane@gmail.com");
		List<Doctor> doctorList = new ArrayList<>();
		doctorList.add(doctor);
		List<DoctorSpeciality> spList = new ArrayList<>();
		spList.add(doctorSpeciality);
		ObjectMapper mapper = new ObjectMapper();
		String doctorJson = mapper.writeValueAsString(doctorList);
		when(doctorSpecialityRepository.findAllDoctor(1)).thenReturn(spList);
		when(doctorRepository.findById(101)).thenReturn(Optional.of(doctor));
		
		mvc.perform(get("/speciality/list/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(doctorJson));
	}
	
	@Test
	void testListAllDoctorfailure() throws Exception {
		DoctorSpeciality doctorSpeciality = new DoctorSpeciality(101, 1);
		List<DoctorSpeciality> spList = new ArrayList<>();
		spList.add(doctorSpeciality);
		when(doctorSpecialityRepository.findAllDoctor(1)).thenReturn(spList);
		when(doctorRepository.findById(101)).thenReturn(Optional.empty());
		
		mvc.perform(get("/speciality/list/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Doctor dont have Speciality"));
	}

	@Test
	void testAddDoctorTOSpeciality() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String doctorJson = mapper.writeValueAsString(doctorSpeciality);
		when(doctorRepository.existsById(doctorSpeciality.getDoctorId())).thenReturn(true);
		when(specialityRepository.existsById(doctorSpeciality.getSpecialityId())).thenReturn(true);
		when(doctorSpecialityRepository.findByDoctorIdandSpecialityId(doctorSpeciality.getDoctorId(), doctorSpeciality.getSpecialityId())).thenReturn(Optional.empty());
		when(doctorSpecialityRepository.save(doctorSpeciality)).thenReturn(doctorSpeciality);
		
		mvc.perform(post("/speciality/addDoctor").content(doctorJson).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Doctore Added to Speciality"));
	}
	
	@Test
	void testAddDoctorTOSpecialityfailure() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String doctorJson = mapper.writeValueAsString(doctorSpeciality);
		when(doctorRepository.existsById(doctorSpeciality.getDoctorId())).thenReturn(true);
		when(specialityRepository.existsById(doctorSpeciality.getSpecialityId())).thenReturn(true);
		when(doctorSpecialityRepository.findByDoctorIdandSpecialityId(doctorSpeciality.getDoctorId(), doctorSpeciality.getSpecialityId())).thenReturn(Optional.of(doctorSpeciality));
		when(doctorSpecialityRepository.save(doctorSpeciality)).thenReturn(doctorSpeciality);
		
		mvc.perform(post("/speciality/addDoctor").content(doctorJson).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Doctor with speciality Alredy there"));
	}
	
	@Test
	void testAddDoctorTOSpecialityFailure() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String doctorJson = mapper.writeValueAsString(doctorSpeciality);
		when(doctorRepository.existsById(doctorSpeciality.getDoctorId())).thenReturn(false);
		when(specialityRepository.existsById(doctorSpeciality.getSpecialityId())).thenReturn(true);
		
		mvc.perform(post("/speciality/addDoctor").content(doctorJson).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Doctor Data is Not available"));
	}
	
	@Test
	void testAddDoctorTOSpecialityFailure1() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String doctorJson = mapper.writeValueAsString(doctorSpeciality);
		when(doctorRepository.existsById(doctorSpeciality.getDoctorId())).thenReturn(true);
		when(specialityRepository.existsById(doctorSpeciality.getSpecialityId())).thenReturn(false);
		
		mvc.perform(post("/speciality/addDoctor").content(doctorJson).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Doctor Data is Not available"));
	}
	
	@Test
	void testRemoveDoctor() throws Exception {
		when(doctorSpecialityRepository.findByDoctorIdandSpecialityId(101, 1)).thenReturn(Optional.of(doctorSpeciality));
		
		mvc.perform(delete("/speciality/removeDoctor/101/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Doctore Remove from Speciality"));
	}

	@Test
	void testRemoveDoctorfailure() throws Exception {
		when(doctorSpecialityRepository.findByDoctorIdandSpecialityId(101, 1)).thenReturn(Optional.empty());
		
		mvc.perform(delete("/speciality/removeDoctor/101/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Doctor Data is Not available"));
	}

}
