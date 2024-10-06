package hr.algebra.healthyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/api")
public class HealthyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthyappApplication.class, args);
	}

}
