package com.demo.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entites.Patient;



public interface PatientRepository  extends JpaRepository<Patient, Integer>{
	
	@Query("select p from Patient p where p.firstName =:firstName")
	public List<Patient> findAllByName(String firstName);
	
	
	@Query("UPDATE Patient p set p.firstName=:firstName , p.lastName=:lastName, p.email=:email where patientId=:patientId")
	@Modifying
	@Transactional
	public int update(Integer patientId, String firstName, String lastName, String email);
	
	public boolean existsByEmail(String email);

}
