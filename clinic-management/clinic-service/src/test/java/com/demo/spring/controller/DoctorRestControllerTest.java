package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Doctor;
import com.demo.spring.repository.DoctorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DoctorRestControllerTest {

	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	DoctorRepository repository;
	
	@Test
	void testListDoctorData() throws Exception {
		Doctor doctor = new Doctor(100, "harsh", "mane", "harshmane@gmail.com");
		List<Doctor> doctorList = new ArrayList<>();
		doctorList.add(doctor);
		ObjectMapper mapper = new ObjectMapper();
		String doctorJson = mapper.writeValueAsString(doctorList);
		when(repository.findAll()).thenReturn(doctorList);
		
		mvc.perform(get("/doctor/list"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(doctorJson));

	}

	
	@Test
	void testListDoctorDataFailure() throws Exception {
		List<Doctor> doctorList = new ArrayList<>();
		when(repository.findAll()).thenReturn(doctorList);
		
		mvc.perform(get("/doctor/list"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Doctor Data is Not available"));

	}

}
