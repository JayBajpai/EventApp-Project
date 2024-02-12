package com.cts.eventList;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@OpenAPIDefinition(info = @Info(title = "Event API", version = "1.0", description = "EventService"))
@SpringBootApplication
@EnableEurekaClient

public class EventListApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventListApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemplate() {

		return new RestTemplate();
	}
}
