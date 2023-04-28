package com.demo.spring.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Credential;
import com.demo.spring.entity.CredentialDTO;
import com.demo.spring.exception.UseNotFoundException;
import com.demo.spring.repository.CredentialRepository;
import com.demo.spring.util.Message;

@Service
public class CredentialService {
	
	@Autowired
	CredentialRepository credentialRepository;
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	public Message findCredential(CredentialDTO credentialDTO) throws UseNotFoundException {
		List<Credential> credentialList = credentialRepository.findByUsername(credentialDTO.getUsername());
		if(credentialList.isEmpty()) {
			logger.error("invalid username and password");
			throw new UseNotFoundException();
		}else {
			if(credentialDTO.getPassword().equals(credentialList.get(0).getPassword())) {
				logger.info("user login Successfully");
				return new Message("login Successfully");
			}else {
				logger.error("invalid username and password");
				throw new UseNotFoundException();
			}
		}
	}
	
	
	public Message updateCredential(CredentialDTO credentialDTO) throws UseNotFoundException {
		if(credentialRepository.existsByUsername(credentialDTO.getUsername())) {
			credentialRepository.updateCredential(credentialDTO.getUsername(), credentialDTO.getPassword());
			logger.info("Credential Update successfully");
			return new Message("Credential Updated");
		}else {
			logger.error("user Not Found");
			throw new UseNotFoundException();
		}
	}
	

}
