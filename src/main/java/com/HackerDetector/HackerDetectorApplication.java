package com.HackerDetector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.HackerDetector.controllers","com.HackerDetector.service"})
public class HackerDetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackerDetectorApplication.class, args);
	}

}
