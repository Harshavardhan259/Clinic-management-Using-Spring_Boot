package com.demo.spring.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.PatientDiagnostic;

public interface PatientDiagnosticRepository extends JpaRepository<PatientDiagnostic, Integer> {

	 @Query(value="insert into patient_diagnostic (diagnostic_Id,patient_Id) values (:diagnosticId ,:patientId) ",nativeQuery = true)
	 @Modifying
	 @Transactional
	 public Integer addDiagnosticToPatient(Integer diagnosticId,Integer patientId);
}
