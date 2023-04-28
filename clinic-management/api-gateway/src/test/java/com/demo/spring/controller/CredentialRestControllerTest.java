package com.demo.spring.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.demo.spring.entity.Credential;
import com.demo.spring.util.Message;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CredentialRestControllerTest {

	@Autowired
	TestRestTemplate testRestTemplate;

	@LocalServerPort
	Integer port;

	

	@Test
	void testLoginSuccess() throws Exception {
		Credential credential1 = new Credential("harsh123", "harsh456");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credential> req = new HttpEntity<>(credential1, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/credential/login",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals("login Successfully", resp2.getBody().getStatus());
	}

	@Test
	void testLoginfailure1() throws Exception {
		Credential credential2 = new Credential("harsh123", "harsh4");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credential> req = new HttpEntity<>(credential2, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/credential/login",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals("invalid username and password", resp2.getBody().getStatus());
	}

	@Test
	void testLoginfailure2() throws Exception {
		Credential credential3 = new Credential("harsh", "harsh456");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credential> req = new HttpEntity<>(credential3, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/credential/login",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals("invalid username and password", resp2.getBody().getStatus());
	}

	@Test
	void testUpdate() {
		Credential credential3 = new Credential("harsh123", "harsh456");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credential> req = new HttpEntity<>(credential3, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/credential/update",
				HttpMethod.PATCH, req, Message.class);
		Assertions.assertEquals("Credential Updated", resp2.getBody().getStatus());
		
	}
	
	@Test
	void testUpdateFailure() {
		Credential credential3 = new Credential("harsh1", "harsh456");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credential> req = new HttpEntity<>(credential3, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/credential/update",
				HttpMethod.PATCH, req, Message.class);
		Assertions.assertEquals("invalid username and password", resp2.getBody().getStatus());
		
	}


}
