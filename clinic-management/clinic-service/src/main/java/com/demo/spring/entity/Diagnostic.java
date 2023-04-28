package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DIAGNOSTIC")
public class Diagnostic {

	@Id
    @SequenceGenerator(sequenceName = "Diagnostic_sequence",initialValue = 7, allocationSize = 1, name = "DIAGNOSTIC_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "DIAGNOSTIC_ID")
	@Column(name = "DIAGNOSTIC_ID")
	private Integer diagnosticId;
	@Column(name = "DIAGNOSTIC_NAME")
	private String diagnosticName;

	public Diagnostic() {
	
	}

	public Diagnostic(Integer diagnosticId, String diagnosticName) {
		this.diagnosticId = diagnosticId;
		this.diagnosticName = diagnosticName;
	}

	public Integer getDiagnosticId() {
		return diagnosticId;
	}

	public String getDiagnosticName() {
		return diagnosticName;
	}

	public void setDiagnosticId(Integer diagnosticId) {
		this.diagnosticId = diagnosticId;
	}

	public void setDiagnosticName(String diagnosticName) {
		this.diagnosticName = diagnosticName;
	}
	

}
