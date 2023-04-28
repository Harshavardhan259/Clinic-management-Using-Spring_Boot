package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PATIENT_DIAGNOSTIC")
public class PatientDiagnostic {

	@Id
	@Column(name="DIAGNOSTIC_ID")
	private Integer diagnosticId;
	@Column(name="PATIENT_ID")
	private Integer patientId;
	
	public PatientDiagnostic() {
		
	}

	public PatientDiagnostic(Integer diagnosticId, Integer patientId) {
		this.diagnosticId = diagnosticId;
		this.patientId = patientId;
	}

	public Integer getDiagnosticId() {
		return diagnosticId;
	}

	public void setDiagnosticId(Integer diagnosticId) {
		this.diagnosticId = diagnosticId;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	
	
}
