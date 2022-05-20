package ibm.java.academy.services.eduardocortes.service;

import ibm.java.academy.services.eduardocortes.constant.BusinessErrorMessages;
import ibm.java.academy.services.eduardocortes.entity.Student;
import ibm.java.academy.services.eduardocortes.exception.ResourceNotFoundException;
import ibm.java.academy.services.eduardocortes.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Eduardo Cortés
 */

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student add(Student student) {
        log.info("Saving new student: {}", student.getFirstName().concat(StringUtils.SPACE).concat(student.getLastName()));
        return studentRepository.save(student);
    }

    public Student findById(Long id) {
        log.info("Fetching student by id: {}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(BusinessErrorMessages.STUDENT_ID_INVALID + id));
    }

    public boolean findStudent(final Student student) {
        log.info("Finding student by firstName: {} and lastName: {} and birthdate: {}",
                student.getFirstName(),
                student.getLastName(),
                student.getBirthDate());
        boolean isExist = studentRepository.findByFirstNameAndLastNameAndBirthDate(student.getFirstName(), student.getLastName(), student.getBirthDate()).isPresent();
        log.info(isExist ? "Exist student" : "Doesn't exist student");
        return isExist;
    }

}
