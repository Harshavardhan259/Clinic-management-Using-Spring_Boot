package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.entity.DiagnosticDTO;
import com.demo.spring.entity.DiagnosticDTO2;
import com.demo.spring.entity.Message;

@Controller
public class DiagnosticUIController {

	@Autowired
	RestTemplate restTemplate;
	
	private String accept = "Accept";
	
	@GetMapping(path="/diagnosticList")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<List<DiagnosticDTO>> response = restTemplate.exchange("http://localhost:9194/diagnostic/list", HttpMethod.GET, request, new ParameterizedTypeReference<List<DiagnosticDTO>>() {});
		mv.addObject("diagnosticList", response.getBody());
		mv.setViewName("diagnostic/diagnosticList");
		return mv;
	}
	
	@PostMapping(path="/addTest")
	public ModelAndView registerOne(DiagnosticDTO diagnosticDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<DiagnosticDTO> request = new HttpEntity<>(diagnosticDTO,headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/diagnostic/addTest", HttpMethod.POST, request, Message.class);
		mv.addObject("message", response.getBody());
		mv.setViewName("diagnostic/responce");
		return mv;
	}
	
	@GetMapping(path="/deleteTest")
	public ModelAndView delete(@RequestParam(name="diagnosticId" , required = true) int diagnosticId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/diagnostic/removeTest/"+diagnosticId, HttpMethod.DELETE, request, Message.class);
		mv.addObject("message", response.getBody());
		mv.setViewName("diagnostic/responce");
		return mv;
	}
	
	@PostMapping(path="/addPatient")
	public ModelAndView addTestToPatient(DiagnosticDTO2 diagnosticDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<DiagnosticDTO2> request = new HttpEntity<>(diagnosticDTO,headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/diagnostic/addPatient/"+diagnosticDTO.getPatientId(), HttpMethod.POST, request, Message.class);
		mv.addObject("message", response.getBody());
		mv.setViewName("diagnostic/responce");
		return mv;
	}
	
}
