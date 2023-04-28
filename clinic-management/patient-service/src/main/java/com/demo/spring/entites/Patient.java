package com.demo.spring.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="PATIENT")
public class Patient {
	
	@Id
    @SequenceGenerator(sequenceName = "Patient_sequence",initialValue = 204, allocationSize = 1, name = "Patient_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Patient_ID")
	@Column(name="PATIENT_ID")
	private Integer patientId;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="LAST_NAME")
	private String lastName;
	@Column(name="EMAIL")
	private String email;
	
	public Patient() {
		
	}

	public Patient(Integer patientId, String firstName, String lastName, String email) {
		this.patientId = patientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

}

