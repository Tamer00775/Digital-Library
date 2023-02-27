package ru.alishev.Project2Boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class Project2BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(Project2BootApplication.class, args);
	}

}
