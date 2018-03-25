package ru.belogurow.socialnetworkserver.configs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author alexbelogurow
 */
@ComponentScan(basePackages = {"ru.belogurow.socialnetworkserver.configs",
		"ru.belogurow.socialnetworkserver.users"})

@MapperScan(value = "ru.belogurow.socialnetworkserver.*.mybatis.mapper")
@SpringBootApplication
public class SocialNetworkServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkServerApplication.class, args);
	}
}
