package com.example.wpdelhi.springbootsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSampleApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootSampleApplication.class, args);
		System.out.println("In main");
	}

}