package com.skilldistillery.boxes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WhatsInTheBoxApplication extends SpringBootServletInitializer {
	  @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	    return application.sources(WhatsInTheBoxApplication.class);
	  }

	public static void main(String[] args) {
		SpringApplication.run(WhatsInTheBoxApplication.class, args);
	}

}
