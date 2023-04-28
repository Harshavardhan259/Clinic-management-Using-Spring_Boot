package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	public List<Appointment> findAllByPatientId(Integer patientId);
	
	 public List<Appointment> findByDate(String date);
}
