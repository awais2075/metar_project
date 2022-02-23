package com.metar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MetarApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetarApplication.class, args);
	}

	@Bean
	public ObjectNode objectMapper() {
		return new ObjectMapper().createObjectNode();
	}

}
