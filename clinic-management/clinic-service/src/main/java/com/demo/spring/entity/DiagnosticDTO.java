package com.demo.spring.entity;

public class DiagnosticDTO {

	
	private Integer diagnosticId;
	private String diagnosticName;
	
	public DiagnosticDTO() {
		
	}
	
	public DiagnosticDTO(Integer diagnosticId, String diagnosticName) {
		this.diagnosticId = diagnosticId;
		this.diagnosticName = diagnosticName;
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

}
