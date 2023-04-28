package com.demo.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.CredentialDTO;
import com.demo.spring.exception.UseNotFoundException;
import com.demo.spring.service.CredentialService;
import com.demo.spring.util.Message;

@RestController
@RequestMapping("/credential")
public class CredentialRestController {

	@Autowired
	CredentialService credentialService;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> login(@RequestBody CredentialDTO credentialDTO) throws UseNotFoundException{
		logger.info("Call for find Credentials");
		return ResponseEntity.ok(credentialService.findCredential(credentialDTO));
	}
	
	@PatchMapping(path="/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> update(@RequestBody CredentialDTO credentialDTO) throws UseNotFoundException{
		logger.info("Call for update Credentials");
		return ResponseEntity.ok(credentialService.updateCredential(credentialDTO));
	}
	
}
