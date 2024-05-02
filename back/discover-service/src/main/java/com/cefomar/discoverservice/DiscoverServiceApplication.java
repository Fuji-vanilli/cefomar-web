package com.cefomar.discoverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoverServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoverServiceApplication.class, args);
	}

}
