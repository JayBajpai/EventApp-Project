package com.WishlistService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(info = @Info(title = "Event API", version = "1.0", description = "WishlistService"))
@SpringBootApplication
public class WishlistServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishlistServiceApplication.class, args);

	}

}
