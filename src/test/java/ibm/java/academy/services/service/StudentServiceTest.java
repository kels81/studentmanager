package ibm.java.academy.services.service;

import ibm.java.academy.services.constant.BusinessErrorMessages;
import ibm.java.academy.services.entity.Student;
import ibm.java.academy.services.entity.enums.GenderEnum;
import ibm.java.academy.services.exception.ResourceNotFoundException;
import ibm.java.academy.services.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Eduardo Cortés
 */

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    private static final Long ID = 1L;
    private static final Long INVALID_ID = 10L;
    private static final String FIRST_NAME = "Manuel";
    private static final String LAST_NAME = "Vargas";
    private static final LocalDate BIRT_DATE = LocalDate.of(1995, 11, 6);
    private static final GenderEnum GENDER = GenderEnum.MALE;


    @Test
    void whenAddStudentIsCreated() {
        Student student = getStudent();
        when(studentRepository.save(student)).thenReturn(student);
        Student studentOutput = studentService.add(student);

        assertNotNull(studentOutput);
        assertEquals(student.getFirstName(), studentOutput.getFirstName());
    }

    @Test
    void whenFindByIdStudentIsPresent() {
        when(studentRepository.findById(ID)).thenReturn(Optional.of(getStudent()));
        Student studentOutput = studentService.findById(ID);

        assertNotNull(studentOutput);
        assertEquals(FIRST_NAME, studentOutput.getFirstName());
    }

    @Test
    void whenFindByIdStudentIsNotPresent() {
        when(studentRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> studentService.findById(INVALID_ID));

        assertEquals(BusinessErrorMessages.STUDENT_ID_INVALID + INVALID_ID, exception.getMessage());
    }

    @Test
    void findAllStudents() {
        when(studentRepository.findAll()).thenReturn(getLstStudent());
        List<Student> lstStudent = studentService.findAll();
        assertNotNull(lstStudent);
        assertEquals(1, lstStudent.size());
    }

    private Student getStudent() {
        Student student = new Student();
        student.setId(ID);
        student.setFirstName(FIRST_NAME);
        student.setLastName(LAST_NAME);
        student.setBirthDate(BIRT_DATE);
        student.setGender(GENDER.getKey());
        return student;
    }

    private List<Student> getLstStudent() {
        List<Student> lstStudent = new ArrayList<>();
        lstStudent.add(getStudent());
        return lstStudent;
    }
}