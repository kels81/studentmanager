package ibm.java.academy.services.service;

import ibm.java.academy.services.constant.BusinessErrorMessages;
import ibm.java.academy.services.entity.Student;
import ibm.java.academy.services.exception.ResourceNotFoundException;
import ibm.java.academy.services.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public List<Student> findAll() {
        log.info("Fetching all students");
        return studentRepository.findAll();
    }

}
