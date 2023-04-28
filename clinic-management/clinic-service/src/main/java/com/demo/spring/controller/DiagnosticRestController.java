package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Diagnostic;
import com.demo.spring.entity.DiagnosticDTO;
import com.demo.spring.exceptions.DiagnosticNotFoundException;
import com.demo.spring.exceptions.DiagnosticTestAlredyExistsException;
import com.demo.spring.exceptions.PatientNotFoundException;
import com.demo.spring.services.DiagnosticService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/diagnostic")
public class DiagnosticRestController {

	@Autowired
	DiagnosticService diagnosticService;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@GetMapping(path="/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.diagnostic.list")
	public ResponseEntity<List<Diagnostic>> listAllDiagnostics() {
		logger.info("Call to service List All Diagnostic in clinic");
			return ResponseEntity.ok(diagnosticService.listAll());
	}
	
	@PostMapping(path="/addTest" ,consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.diagnostic.addTest")
	public ResponseEntity<Message> addTest(@RequestBody DiagnosticDTO dto) throws DiagnosticTestAlredyExistsException{
		logger.info("Call to service Add Diagnostic to Diagnostic list");
		return ResponseEntity.ok(diagnosticService.addTests(dto));
	}
	
	@DeleteMapping(path="/removeTest/{diagnosticId}" , produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.diagnostic.removeTest")
	public ResponseEntity<Message> removeTest(@PathVariable("diagnosticId") Integer id) throws DiagnosticNotFoundException{
		logger.info("Call to service Remove Diagnostic from Diagnostic list");
		return ResponseEntity.ok(diagnosticService.removeTests(id));
	}
	
	@PostMapping(path="/addPatient/{patientId}" ,consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value="request.diagnostic.addPatient")
	public ResponseEntity<Message> addpatient(@RequestBody DiagnosticDTO diagnosticDTO ,@PathVariable("patientId") Integer id)
		throws PatientNotFoundException ,DiagnosticNotFoundException{
		logger.info("Call to service Add Diagnostic to Patient");
		return ResponseEntity.ok(diagnosticService.addDiagnosticTest(diagnosticDTO, id));
	}
}
