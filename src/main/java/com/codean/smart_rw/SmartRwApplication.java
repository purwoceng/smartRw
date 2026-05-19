package com.codean.smart_rw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartRwApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartRwApplication.class, args);
	}

}
