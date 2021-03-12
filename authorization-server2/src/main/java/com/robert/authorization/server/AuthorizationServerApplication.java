package com.robert.authorization.server;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		
		  SpringApplication.run(AuthorizationServerApplication.class, args);
		 
		/*
		 * SpringApplication app = new
		 * SpringApplication(AuthorizationServerApplication.class);
		 * app.setBannerMode(Banner.Mode.OFF); app.run(args);
		 */
	}

}
