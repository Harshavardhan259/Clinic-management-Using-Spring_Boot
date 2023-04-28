package com.demo.spring.entity;

public class DoctorSpecialityDTO {

	
	private Integer doctorId;
	private Integer specialityId;

	public DoctorSpecialityDTO() {
		
	}
	
	public DoctorSpecialityDTO(Integer doctorId, Integer specialityId) {
		super();
		this.doctorId = doctorId;
		this.specialityId = specialityId;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Integer specialityId) {
		this.specialityId = specialityId;
	}

}
