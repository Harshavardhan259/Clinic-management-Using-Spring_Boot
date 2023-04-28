package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class UiServiceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(UiServiceApplication.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/find").setViewName("patient/findone");
		registry.addViewController("/patient").setViewName("patient/home");
		registry.addViewController("/details").setViewName("patient/patient_detaile");
		registry.addViewController("/list").setViewName("patient/list");
		registry.addViewController("/update").setViewName("patient/updateOne");
		registry.addViewController("/registerPatient").setViewName("patient/registerOne");
		
		registry.addViewController("/appointment").setViewName("appointment/home");
		registry.addViewController("/listAppointment").setViewName("appointment/listAppointment");
		registry.addViewController("/listdoctor").setViewName("appointment/listdate");
		registry.addViewController("/registerAppointment").setViewName("appointment/addAppointment");
		
		registry.addViewController("/doctor").setViewName("doctor/home");
		
		registry.addViewController("/speciality").setViewName("speciality/home");
		registry.addViewController("/listDoctorSpeciality").setViewName("speciality/listDoctorSpeciality");
		registry.addViewController("/registerDoctor").setViewName("speciality/addDoctor");
		registry.addViewController("/removeDoctor").setViewName("speciality/remove");
		
		registry.addViewController("/diagnostic").setViewName("diagnostic/home");
		registry.addViewController("/addtest").setViewName("diagnostic/addDiagnostic");
		registry.addViewController("/removetests").setViewName("diagnostic/removeTest");
		registry.addViewController("/addpatient").setViewName("diagnostic/addPatient");
		
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/clinic").setViewName("clinicService");
		
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/").setViewName("base_page");
		
	}
	
	@Bean
	RestTemplate restTemplate() {
	 	 return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
			
	}

}
