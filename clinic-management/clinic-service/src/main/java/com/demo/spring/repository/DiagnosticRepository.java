package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Diagnostic;

public interface DiagnosticRepository extends JpaRepository<Diagnostic, Integer> {

	public boolean existsByDiagnosticName(String diagnosticName);
}
