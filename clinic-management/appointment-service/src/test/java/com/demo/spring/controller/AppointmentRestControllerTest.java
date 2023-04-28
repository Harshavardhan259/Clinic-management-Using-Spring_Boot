package com.demo.spring.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Appointment;
import com.demo.spring.entity.PatientDTO;
import com.demo.spring.repository.AppointmentRepository;
import com.demo.spring.utiles.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"patientServer=http://localhost:${wiremock.server.port}"})
@AutoConfigureMockMvc
@AutoConfigureWireMock(port =0)
class AppointmentRestControllerTest {

	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	AppointmentRepository apprepository;
	
	String date ="2022-10-01";
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	Integer port;
	
	Appointment appointment = new Appointment(1, 101, 200, date);
	ObjectMapper mapper = new ObjectMapper();
	

	@Test
	void testFindAllforPatient()  throws Exception{
		List<Appointment> appointmentList = new ArrayList<>();
		appointmentList.add(appointment);
		when(apprepository.findAllByPatientId(200)).thenReturn(appointmentList);
		
		mvc.perform(get("/appointment/listPatient/200"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"[{'appointmentId':1,'doctorId':101,'patientId':200,'date':'2022-10-01'}]"));

	}
	
	@Test
	void testFindAllforPatientFailure()  throws Exception{
		List<Appointment> appointmentList = new ArrayList<>();
		when(apprepository.findAllByPatientId(200)).thenReturn(appointmentList);
		
		mvc.perform(get("/appointment/listPatient/200"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Appointment for Patient Not fund"));

	}

	@Test
	void testFindAllforDoctore() throws Exception {
		List<Appointment> appointmentList = new ArrayList<>();
		appointmentList.add(appointment);
		when(apprepository.findByDate(appointment.getDate())).thenReturn(appointmentList);
		
		mvc.perform(get("/appointment/listDoctore?date=2022-10-01"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(
					"[{'appointmentId':1,'doctorId':101,'patientId':200,'date':'2022-10-01'}]"));

	}
	
	@Test
	void testFindAllforDoctoreFailure() throws Exception {
		List<Appointment> appointmentList = new ArrayList<>();
		when(apprepository.findByDate(appointment.getDate())).thenReturn(appointmentList);
		
		mvc.perform(get("/appointment/listDoctore?date=2022-10-01"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Apoointment not Found for Doctor"));

	}
	
	
	@Test
    void testaddvisit() throws Exception {
		PatientDTO patientDTO = new PatientDTO(200,"harsh","mane","gmail.com");
        Message message = new Message("Appointment is Added");
        String messageJson = mapper.writeValueAsString(message);
        String patientJson = mapper.writeValueAsString(patientDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Appointment> req = new HttpEntity<>(appointment, headers);
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo("/patient/"+appointment.getPatientId()))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));
        ResponseEntity<String> response2 = testRestTemplate
                .exchange("http://localhost:" + port + "/appointment/addVisit", HttpMethod.POST, req, String.class);
        Assertions.assertTrue(response2.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(response2.getBody().contains(messageJson));
    }
	
	@Test
    void testaddvisitFailure() throws Exception {
		PatientDTO patientDTO = new PatientDTO();
        Message message = new Message("Patient Not Registered");
        String messageJson = mapper.writeValueAsString(message);
        String patientJson = mapper.writeValueAsString(patientDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        Appointment appointment1 = new Appointment(1, 101, 201, date);
        HttpEntity<Appointment> req = new HttpEntity<>(appointment1, headers);
        
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo("/patient/201"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));
        
        ResponseEntity<String> response2 = testRestTemplate
                .exchange("http://localhost:" + port + "/appointment/addVisit", HttpMethod.POST, req, String.class);
        
       System.out.println(response2.getBody());
        Assertions.assertTrue(response2.getBody().contains(messageJson));
    }
	

}
