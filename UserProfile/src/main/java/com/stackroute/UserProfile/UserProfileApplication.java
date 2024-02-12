package com.stackroute.UserProfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(info = @Info(title = "UserDetails API", version = "1.0", description = "UserDetails API"))
@SpringBootApplication
@EnableDiscoveryClient
public class UserProfileApplication {
 
	public static void main(String[] args) {
		SpringApplication.run(UserProfileApplication.class, args);
	}

}
  