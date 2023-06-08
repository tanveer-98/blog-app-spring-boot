package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	// we can define spring beans in this class
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	// model Mapper is used to reduce the boiler plate code for converting entity to dto and vice versa

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
