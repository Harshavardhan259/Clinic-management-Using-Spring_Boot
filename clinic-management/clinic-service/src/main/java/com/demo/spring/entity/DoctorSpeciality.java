package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DOCTOR_SPECIALITY")
public class DoctorSpeciality{
	
	@Id
	@Column(name = "DOCTOR_ID")
	private Integer doctorId;
	@Column(name = "SPECIALITY_ID")
	private Integer specialityId;
	

	public DoctorSpeciality() {
		
	}

	public DoctorSpeciality(Integer doctorId, Integer specialityId) {
		this.doctorId = doctorId;
		this.specialityId = specialityId;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public Integer getSpecialityId() {
		return specialityId;
	}

}
