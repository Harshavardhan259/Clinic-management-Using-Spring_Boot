package com.demo.spring.entity;

public class AppointmentDTO {
	
	
	private Integer appointmentId;
	private Integer doctorId;
	private Integer patientId;
	private String date;
	
	public AppointmentDTO() {
		
	}
	
	
	
	public AppointmentDTO(Integer appointmentId, Integer doctorId, Integer patientId, String date) {
		this.appointmentId = appointmentId;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.date = date;
	}



	public Integer getAppointmentId() {
		return appointmentId;
	}


	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}


	public Integer getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}


	public Integer getPatientId() {
		return patientId;
	}


	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
}
