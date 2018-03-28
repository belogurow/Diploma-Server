package ru.belogurow.socialnetworkserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author alexbelogurow
 */
@SpringBootApplication
public class SocialNetworkServerApplication {

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);
	}

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkServerApplication.class, args);
	}
}
