package com.example.eduquizweb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.eduquizweb", "com.example.eduquizcommon"})
@EntityScan("com.example.eduquizcommon.entity")
@EnableJpaRepositories(basePackages = "com.example.eduquizcommon.repository")
public class EduQuizWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(EduQuizWebApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}

}
