package com.example.pwr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PwrApplication {

	public static void main(String[] args) {
		System.setProperty("vaadin.whitelisted-packages", "br/com/jfsistemas/");
		SpringApplication.run(PwrApplication.class, args);
	}

}
