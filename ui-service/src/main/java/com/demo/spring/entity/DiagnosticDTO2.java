package com.demo.spring.entity;

public class DiagnosticDTO2 {

	
	private Integer diagnosticId;
	private String diagnosticName;
	private String patientId;
	
	public DiagnosticDTO2() {
		
	}

	public DiagnosticDTO2(Integer diagnosticId, String diagnosticName, String patientId) {
		super();
		this.diagnosticId = diagnosticId;
		this.diagnosticName = diagnosticName;
		this.patientId = patientId;
	}

	public Integer getDiagnosticId() {
		return diagnosticId;
	}

	public void setDiagnosticId(Integer diagnosticId) {
		this.diagnosticId = diagnosticId;
	}

	public String getDiagnosticName() {
		return diagnosticName;
	}

	public void setDiagnosticName(String diagnosticName) {
		this.diagnosticName = diagnosticName;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	

}
