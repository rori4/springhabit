package org.rangelstoilov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringhabitApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringhabitApplication.class, args);
	}
}
