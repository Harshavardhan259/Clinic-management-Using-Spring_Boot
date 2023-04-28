package com.demo.spring.entity;

public class PatientDTO {
	

	private Integer patientId;
	private String firstName;
	private String lastName;
	private String email;
	
	public PatientDTO() {
		
	}
	

	
	public PatientDTO(Integer patientId, String firstName, String lastName, String email) {
		this.patientId = patientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}



	public Integer getPatientId() {
		return patientId;
	}



	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}



	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
	
}

