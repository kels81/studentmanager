package ibm.java.academy.services.repository;

import ibm.java.academy.services.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Eduardo Cortés
 */

public interface StudentRepository extends JpaRepository<Student, Long> {
}
