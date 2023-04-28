package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

}
