package com.finalproject.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("1111"));
		SpringApplication.run(DemoApplication.class, args);
	}

}