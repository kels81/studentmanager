package ibm.java.academy.services;

import ibm.java.academy.services.entity.Student;
import ibm.java.academy.services.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

/**
 * @author Eduardo Cortés
 */

@SpringBootApplication
public class StudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }

    @Bean
    CommandLineRunner run(StudentRepository studentRepository) {
        return args -> {
            studentRepository.save(new Student(null, "Mario", "Perez", LocalDate.of(2017, 1, 13), 'M'));
            studentRepository.save(new Student(null, "Laura", "Sotelo", LocalDate.of(2002, 10, 22), 'F'));
            studentRepository.save(new Student(null, "Saul", "Cortés", LocalDate.of(2003, 9, 3), 'M'));
            studentRepository.save(new Student(null, "Miriam", "Vargas", LocalDate.of(2009, 10, 2), 'F'));
        };
    }

}
