package com.demo.spring.entites;

public class PatientDTO {
	

	private Integer patientId;
	private String firstName;
	private String lastName;
	private String email;
	
	

	public PatientDTO(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}


	public Integer getPatientId() {
		return patientId;
	}


	public String getfirstName() {
		return firstName;
	}


	public String getlastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

}

