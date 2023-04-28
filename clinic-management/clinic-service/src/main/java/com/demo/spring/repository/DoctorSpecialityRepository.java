package com.demo.spring.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.DoctorSpeciality;

public interface DoctorSpecialityRepository extends JpaRepository<DoctorSpeciality, Integer> {

	@Query("select d from DoctorSpeciality d where d.specialityId=:specialityId")
	public List<DoctorSpeciality> findAllDoctor(Integer specialityId);
	
	 @Query(value="insert into doctor_speciality (doctor_Id,speciality_Id) values (:doctorId,:specialityId) ",nativeQuery = true)
	 @Modifying
	 @Transactional
	 public Integer addDoctorToSpeciality(Integer doctorId,Integer specialityId);
	 
	 @Query("select d from DoctorSpeciality d where d.specialityId=:specialityId and d.doctorId=:doctorId")
	 public Optional<DoctorSpeciality> findByDoctorIdandSpecialityId(Integer doctorId, Integer specialityId);
	 
	 @Query("delete from DoctorSpeciality d where d.specialityId=:specialityId and d.doctorId=:doctorId")
	 @Modifying
	 @Transactional
	 public void deleteDoctor(Integer doctorId, Integer specialityId);

	 
}
