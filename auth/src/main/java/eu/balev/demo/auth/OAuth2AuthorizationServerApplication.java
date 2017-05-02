package eu.balev.demo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Central entry point for this application.
 * 
 * @author LBalev
 *
 */
@SpringBootApplication
public class OAuth2AuthorizationServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(OAuth2AuthorizationServerApplication.class, args);
	}
}