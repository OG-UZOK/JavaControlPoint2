package com.example.JavaControlPoint2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
@OpenAPIDefinition
public class JavaControlPoint2Application {

	public static void main(String[] args) {
		SpringApplication.run(JavaControlPoint2Application.class, args);
	}

}
