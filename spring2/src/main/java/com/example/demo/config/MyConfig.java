package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
	public MyConfig() {
		System.out.println("MyConfig()");
	}

	@Value("${brad.company.name}")
	private String companyName;
	
	@Bean
	public String companyName() {
		System.out.println("companyName()");
		return companyName;
	}
	
}