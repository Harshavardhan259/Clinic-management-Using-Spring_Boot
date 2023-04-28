package com.demo.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Integer> {

	public List<Credential> findByUsername(String username);
	
	public boolean existsByUsername(String username);
	
	@Query("UPDATE Credential p set p.password=:password where username=:username")
	@Modifying
	@Transactional
	public Integer updateCredential(String username, String password);
}
