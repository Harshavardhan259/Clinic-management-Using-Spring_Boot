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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.entity.DoctorDTO;

@Controller
public class DoctorUIController {

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping(path="/doctorList")
	public ModelAndView findAllApointment() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<List<DoctorDTO>> response = restTemplate.exchange("http://localhost:9194/doctor/list", HttpMethod.GET, request, new ParameterizedTypeReference<List<DoctorDTO>>() {});
		mv.addObject("doctorList", response.getBody());
		mv.setViewName("doctor/doctorList");
		return mv;
	}
}
