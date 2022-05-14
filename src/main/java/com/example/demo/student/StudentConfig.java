package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student mariam = new Student(
				"Mariam",
				"mariam@gmail.com",
				LocalDate.of(1995, Month.JANUARY, 1)
			);

            Student nova = new Student(
				"Nova",
				"nova@gmail.com",
				LocalDate.of(1996, Month.NOVEMBER, 15)
			);

            studentRepository.saveAll(List.of(mariam, nova));
        };
    }
}
