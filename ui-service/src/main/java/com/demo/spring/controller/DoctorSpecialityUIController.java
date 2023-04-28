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

import com.demo.spring.entity.DoctorDTO;
import com.demo.spring.entity.DoctorSpecialityDTO;
import com.demo.spring.entity.Message;

@Controller
public class DoctorSpecialityUIController {

	
	@Autowired
	RestTemplate restTemplate;
	
	private String accept ="Accept";
	
	@GetMapping(path="/listdoctorInSpeciality")
	public ModelAndView findAll(@RequestParam(name="specialityId", required = true) int specialityId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		
		String str = restTemplate.exchange("http://localhost:9194/speciality/list/"+specialityId, HttpMethod.GET, request, String.class).getBody();
	    if (str!= null && str.equals("{\"status\":\"Doctor Data is Not available\"}")) {
	            mv.addObject("message", "Speciality is not found");
	            mv.setViewName("speciality/responce2");
	            return mv;
	    }else {
	    	ResponseEntity<List<DoctorDTO>> response = restTemplate.exchange("http://localhost:9194/speciality/list/"+specialityId, HttpMethod.GET, request, new ParameterizedTypeReference<List<DoctorDTO>>() {});
			mv.addObject("doctorList", response.getBody());
			mv.setViewName("speciality/doctorList");
			return mv;
	    }
	        
		
	}
	
	@PostMapping(path="/addDoctor")
	public ModelAndView registerOne(DoctorSpecialityDTO doctorSpecialityDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<DoctorSpecialityDTO> request = new HttpEntity<>(doctorSpecialityDTO,headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/speciality/addDoctor", HttpMethod.POST, request, Message.class);
		mv.addObject("message", response.getBody());
		mv.setViewName("speciality/responce");
		return mv;
	}
	
	@GetMapping(path="/deleteDoctor")
	public ModelAndView delete(@RequestParam(name="specialityId", required = true) int specialityId, @RequestParam(name="doctorId" , required = true) int doctorId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/speciality/removeDoctor/"+doctorId+"/"+specialityId, HttpMethod.DELETE, request, Message.class);
		mv.addObject("message", response.getBody());
		mv.setViewName("speciality/responce");
		return mv;
	}
}
