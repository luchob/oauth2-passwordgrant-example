package eu.balev.demo.res;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The central entry point of the application.
 * 
 * @author LBalev
 *
 */
@SpringBootApplication
public class OAuth2ResourceServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(OAuth2ResourceServerApplication.class, args);
	}
}