package com.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.entity.CredentialDTO;
import com.demo.spring.entity.Message;

@Controller
public class LoginController {

	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping(path = "/homepage")
	public ModelAndView login(@RequestParam(name="username" , required = true) String username, @RequestParam(name="password" ,required = true) String password) {
		 CredentialDTO credentialDTO = new CredentialDTO(username, password);
	        ModelAndView mv = new ModelAndView();
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
	        HttpEntity<CredentialDTO> req = new HttpEntity<>(credentialDTO, headers);
	        ResponseEntity<Message> response = restTemplate.exchange("http://localhost:9194/credential/login", HttpMethod.POST,
	                req, Message.class);
	        Message res = response.getBody();
	        System.out.println(res.getStatus());
	        Message msg = new Message("login Successfully");
	        if(res!=null && res.getStatus().equals(msg.getStatus())) {
	        	mv.addObject("message", response.getBody());
                mv.setViewName("index");
                return mv;
	        }else {
	        	mv.addObject("message", response.getBody());
                mv.setViewName("responce");
                return mv;
	        }
	}
}
