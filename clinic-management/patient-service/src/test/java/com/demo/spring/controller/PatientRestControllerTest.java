package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

import com.demo.spring.entites.Patient;
import com.demo.spring.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PatientRestControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	PatientRepository patientRepository;

	Patient patientDTO = new Patient(200, "harsh", "mane", "harshmane@gmail.com");
	
	@Test
	void testFindPatient() throws Exception {
		
		when(patientRepository.findById(200)).thenReturn(Optional.of(patientDTO));

		mvc.perform(get("/patient/200")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.firstName").value("harsh"));

	}

	@Test
    void testFindPatientFailure() throws Exception {
        when(patientRepository.findById(200)).thenReturn(Optional.empty());
        
        mvc.perform(get("/patient/200")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.status").value("patient Not Fund"));
    }

	@Test
	void testRegister() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String patientJson = mapper.writeValueAsString(patientDTO);
		
		when(patientRepository.existsByEmail(patientDTO.getEmail())).thenReturn(false);
		mvc.perform(post("/patient/register").content(patientJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Patient is registered"));

	}
	
	@Test
	void testRegisterFailure() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String patientJson = mapper.writeValueAsString(patientDTO);
		
		when(patientRepository.existsByEmail(patientDTO.getEmail())).thenReturn(true);
		mvc.perform(post("/patient/register").content(patientJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("patient Alredy Exists"));

	}

	@Test
	void testListByPatientName() throws Exception {
		List<Patient> patientList = new ArrayList<>();
		patientList.add(patientDTO);
		ObjectMapper mapper = new ObjectMapper();
		String patientJson = mapper.writeValueAsString(patientList);
		when(patientRepository.findAllByName("harsh")).thenReturn(patientList);
		
		mvc.perform(get("/patient/list/harsh"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(patientJson));

	}
	
	@Test
	void testListByPatientNameFailure() throws Exception {
		List<Patient> patientList = new ArrayList<>();
		when(patientRepository.findAllByName("harsh")).thenReturn(patientList);
		
		mvc.perform(get("/patient/list/harsh"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("patient Not Fund"));

	}

	@Test
	void testUpdatePatienRecord() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String patientJson = mapper.writeValueAsString(patientDTO);
		
		when(patientRepository.existsById(200)).thenReturn(true);
		when(patientRepository.update(patientDTO.getPatientId(),patientDTO.getFirstName(),patientDTO.getLastName(),patientDTO.getEmail())).thenReturn(1);
		mvc.perform(patch("/patient/update").content(patientJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Patient Record is Updated"));
		

	}
	
	@Test
	void testUpdatePatienRecordFailure() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String patientJson = mapper.writeValueAsString(patientDTO);
		
		when(patientRepository.existsById(200)).thenReturn(false);
		mvc.perform(patch("/patient/update").content(patientJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("patient Not Fund"));
		

	}

}
