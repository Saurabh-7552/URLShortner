package com.urlshortner.packetsurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.urlshortner.packetsurl", "controller", "service", "model", "repository"})
@EntityScan(basePackages = {"com.urlshortner.packetsurl", "model"})
@EnableJpaRepositories(basePackages = {"com.urlshortner.packetsurl", "Repository"})
public class PacketsurlApplication {

	public static void main(String[] args) {
		SpringApplication.run(PacketsurlApplication.class, args);
		System.out.print("hello world");
	}

}
