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

import com.demo.spring.entity.Message;
import com.demo.spring.entity.PatientDTO;

@Controller
public class PatientUIController {

	@Autowired
	RestTemplate restTemplate;
	
	private String accept ="Accept";
	
	@GetMapping(path="/findone")
	public ModelAndView findById(@RequestParam(name="id", required = true) int id) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<PatientDTO> response = restTemplate.exchange("http://localhost:9194/patient/"+id, HttpMethod.GET, request, PatientDTO.class);
		mv.setViewName("patient/patient_detaile");
		mv.addObject("patient", response.getBody());
		return mv;
	}
	
	@GetMapping(path="/listAll")
	public ModelAndView findAll(@RequestParam(name="fname", required = true) String fname) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		
		String str = restTemplate.exchange("http://localhost:9194/patient/list/"+fname, HttpMethod.GET, request, String.class).getBody();
	    if (str!= null && str.equals("{\"status\":\"patient Not Fund\"}")) {
	            mv.addObject("message", "patient Not Fund");
	            mv.setViewName("patient/register_success2");
	            return mv;
	    }else {
	    	ResponseEntity<List<PatientDTO>> response = restTemplate.exchange("http://localhost:9194/patient/list/"+fname, HttpMethod.GET, request, new ParameterizedTypeReference<List<PatientDTO>>() {});
			mv.addObject("patientList", response.getBody());
			mv.setViewName("patient/patientList");
			return mv;
	    }
		
		
	}
	
	@PostMapping(path="/registerOne")
	public ModelAndView registerOne(PatientDTO patientDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<PatientDTO> request = new HttpEntity<>(patientDTO,headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/patient/register", HttpMethod.POST, request, Message.class);
		mv.addObject("message", response.getBody());
		mv.setViewName("patient/register_success");
		return mv;
	}
	
	@PostMapping(path="/updateOne")
	public ModelAndView update(PatientDTO patientDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<PatientDTO> request = new HttpEntity<>(patientDTO,headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/patient/update", HttpMethod.PATCH, request, Message.class);
		mv.addObject("message", response.getBody());
		mv.setViewName("patient/register_success");
		return mv;
	}
	
}
