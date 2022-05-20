package ibm.java.academy.services.eduardocortes.repository;

import ibm.java.academy.services.eduardocortes.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Eduardo Cortés
 */

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
}
