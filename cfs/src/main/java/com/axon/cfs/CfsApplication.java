package com.axon.cfs;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@SpringBootApplication(scanBasePackages = "com.axon.cfs")
public class CfsApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CfsApplication.class, args);
	}

}
