package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="APPOINTMENT")
public class Appointment {
	
	@Id
    @SequenceGenerator(sequenceName = "Appointment_sequence",initialValue = 5, allocationSize = 1, name = "Appointment_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Appointment_ID")
	@Column(name="APPOINTMENT_ID")
	private Integer appointmentId;
	@Column(name="DOCTOR_ID")
	private Integer doctorId;
	@Column(name="PATIENT_ID")
	private Integer patientId;
	@Column(name="DATE")
	private String date;
	
	
	public Appointment() {
		
	}


	public Appointment(Integer appointmentId, Integer doctorId, Integer patientId, String date) {
		this.appointmentId = appointmentId;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.date = date;
	}


	public Integer getAppointmentId() {
		return appointmentId;
	}


	public Integer getDoctorId() {
		return doctorId;
	}


	public Integer getPatientId() {
		return patientId;
	}

	public String getDate() {
		return date;
	}
	
}
