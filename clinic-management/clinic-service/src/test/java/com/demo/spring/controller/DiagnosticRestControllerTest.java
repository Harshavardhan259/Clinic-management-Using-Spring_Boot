package com.demo.spring.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
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

import com.demo.spring.entity.Diagnostic;
import com.demo.spring.entity.DiagnosticDTO;
import com.demo.spring.entity.PatientDTO;
import com.demo.spring.repository.DiagnosticRepository;
import com.demo.spring.repository.PatientDiagnosticRepository;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"patientServer=http://localhost:${wiremock.server.port}"})
@AutoConfigureMockMvc
@AutoConfigureWireMock(port =0)
class DiagnosticRestControllerTest {

	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	DiagnosticRepository repository;
	
	@MockBean
	PatientDiagnosticRepository patientDiagnosticRepository;
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	Integer port;
	
	Diagnostic diagnostic = new Diagnostic(1,"CPR");
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	@Test
	void testListAllDiagnostics() throws Exception {
		List<Diagnostic> diagnosticList = new ArrayList<>();
		diagnosticList.add(diagnostic);
		ObjectMapper mapper = new ObjectMapper();
		String diagnosticJson = mapper.writeValueAsString(diagnosticList);
		when(repository.findAll()).thenReturn(diagnosticList);
		
		mvc.perform(get("/diagnostic/list"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(content().json(diagnosticJson));
	}

	@Test
	void testAddTest()  throws Exception{
		String diagnosticJson = mapper.writeValueAsString(diagnostic);
		when(repository.existsByDiagnosticName(diagnostic.getDiagnosticName())).thenReturn(false);
		
		mvc.perform(post("/diagnostic/addTest").content(diagnosticJson).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Test Added"));
	}

	@Test
	void testAddTestFailure()  throws Exception{
		String diagnosticJson = mapper.writeValueAsString(diagnostic);
		when(repository.existsByDiagnosticName(diagnostic.getDiagnosticName())).thenReturn(true);
		
		mvc.perform(post("/diagnostic/addTest").content(diagnosticJson).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Test Alredy Exists"));
	}
	
	@Test
	void testaddTest()  throws Exception{
		String diagnosticJson = mapper.writeValueAsString(diagnostic);
		when(repository.existsById(1)).thenReturn(true);
		
		mvc.perform(post("/diagnostic/addPatient/200").content(diagnosticJson).contentType(MediaType.APPLICATION_JSON_VALUE))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Diagnostic test is added to Patient"));
	}

	@Test
	void testRemoveTest() throws Exception{
		
		when(repository.findById(1)).thenReturn(Optional.of(diagnostic));
		
		mvc.perform(delete("/diagnostic/removeTest/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Test Removed From test List"));
	}
	
	@Test
	void testRemoveTestFailure() throws Exception{
		when(repository.findById(1)).thenReturn(Optional.empty());
		
		mvc.perform(delete("/diagnostic/removeTest/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.status").value("Diagnostic Not Found"));
	}
	
	
	
	@Test
	void testAddDiagnosticTest1()  throws Exception{
		PatientDTO patientDTO = new PatientDTO(200,"harsh","mane","gmail.com");
        Message message = new Message("Diagnostic Not Found");
        String messageJson = mapper.writeValueAsString(message);
        String patientJson = mapper.writeValueAsString(patientDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        DiagnosticDTO diagnostic = new DiagnosticDTO(1,"CPR");
        HttpEntity<DiagnosticDTO> req = new HttpEntity<>(diagnostic, headers);
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo("/patient/200"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));
		
        ResponseEntity<String> response2 = testRestTemplate
                .exchange("http://localhost:" + port + "/diagnostic/addPatient/200", HttpMethod.POST, req, String.class);
        System.out.println(response2.getBody());
        Assertions.assertTrue(response2.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(response2.getBody().contains(messageJson));
	}
	
	@Test
	void testAddDiagnosticTest1Faulure()  throws Exception{
		PatientDTO patientDTO = new PatientDTO();
        Message message = new Message("Patient Not Registered");
        String messageJson = mapper.writeValueAsString(message);
        String patientJson = mapper.writeValueAsString(patientDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        DiagnosticDTO diagnostic = new DiagnosticDTO(1,"CPR");
        HttpEntity<DiagnosticDTO> req = new HttpEntity<>(diagnostic, headers);
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo("/patient/200"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));
		
        ResponseEntity<String> response2 = testRestTemplate
                .exchange("http://localhost:" + port + "/diagnostic/addPatient/200", HttpMethod.POST, req, String.class);
        System.out.println(response2.getBody());
        Assertions.assertTrue(response2.getStatusCode().is2xxSuccessful());
        Assertions.assertTrue(response2.getBody().contains(messageJson));
	}

}
