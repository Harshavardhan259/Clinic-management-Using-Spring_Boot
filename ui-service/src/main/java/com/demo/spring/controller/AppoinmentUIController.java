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

import com.demo.spring.entity.AppointmentDTO;
import com.demo.spring.entity.Message;

@Controller
public class AppoinmentUIController {
	
	@Autowired
	RestTemplate restTemplate;
	
	private String accept="Accept";
	
	@GetMapping(path="/listPatient")
	public ModelAndView findAllApointment(@RequestParam(name="patientId", required = true) int patientId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		
		String str = restTemplate.exchange("http://localhost:9194/appointment/listPatient/"+patientId, HttpMethod.GET, request, String.class).getBody();
	    if (str!= null && str.equals("{\"status\":\"Appointment for Patient Not fund\"}")) {
	            mv.addObject("message", "Appointment for Patient Not fund");
	            mv.setViewName("appointment/responce");
	            return mv;
	    }else {
	    	ResponseEntity<List<AppointmentDTO>> response = restTemplate.exchange("http://localhost:9194/appointment/listPatient/"+patientId, HttpMethod.GET, request, new ParameterizedTypeReference<List<AppointmentDTO>>() {});
			mv.addObject("appointmentList", response.getBody());
			mv.setViewName("appointment/appointmentList");
			return mv;
	    }
		
	}
	
	@GetMapping(path="/listDoctor")
	public ModelAndView findAllApointmentForDoctor(@RequestParam(name="date", required = true) String date) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> request = new HttpEntity<>(headers);
		String str = restTemplate.exchange("http://localhost:9194/appointment/listDoctore?date="+date, HttpMethod.GET, request, String.class).getBody();
	    if (str!= null && str.equals("{\"status\":\"Apoointment not Found for Doctor\"}")) {
	            mv.addObject("message", "Apoointment not Found for Doctor");
	            mv.setViewName("appointment/responce");
	            return mv;
	    }else {
	    	ResponseEntity<List<AppointmentDTO>> response = restTemplate.exchange("http://localhost:9194/appointment/listDoctore?date="+date, HttpMethod.GET, request, new ParameterizedTypeReference<List<AppointmentDTO>>() {});
			mv.addObject("appointmentList", response.getBody());
			mv.setViewName("appointment/appointmentList");
			return mv;
	    }
		
		
	}
	
	@PostMapping(path="/addAppointment")
	public ModelAndView registerOne(AppointmentDTO appointmentDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set(accept, MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AppointmentDTO> request = new HttpEntity<>(appointmentDTO,headers);
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/appointment/addVisit", HttpMethod.POST, request, Message.class);
		mv.addObject("message", response.getBody());
		mv.setViewName("appointment/responce2");
		return mv;
	}
}
