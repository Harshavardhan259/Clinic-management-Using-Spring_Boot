package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DOCTOR")
public class Doctor {

	@Id
	@Column(name="DOCTOR_ID")
	private Integer doctorId;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="LAST_NAME")
	private String lastName;
	@Column(name="EMAIL")
	private String email;

	public Doctor() {
		
	}

	public Doctor(Integer doctorId, String firstName, String lastName, String email) {
		this.doctorId = doctorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Integer getDoctorId() {
		return doctorId;
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
