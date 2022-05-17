package ibm.java.academy.services.eduardocortes.controller;

import ibm.java.academy.services.eduardocortes.dto.GetDataResponse;
import ibm.java.academy.services.eduardocortes.dto.GetStudentIdResponse;
import ibm.java.academy.services.eduardocortes.dto.StudentDto;
import ibm.java.academy.services.eduardocortes.entity.Student;
import ibm.java.academy.services.eduardocortes.entity.enums.GenderEnum;
import ibm.java.academy.services.eduardocortes.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Eduardo Cortés
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/academy")
public class StudentController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/{id}")
    public ResponseEntity<GetDataResponse> getById(@PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        return new ResponseEntity(new GetDataResponse(student.getId(), getFullName(student)), HttpStatus.OK);
    }

    @PostMapping("/student")
    public ResponseEntity<GetStudentIdResponse> add(@RequestBody @Valid StudentDto studentDto) {
        Student addStudent = studentService.add(convertToEntity(studentDto));
        return new ResponseEntity(new GetStudentIdResponse(addStudent.getId()), HttpStatus.CREATED);
    }

    private Student convertToEntity(StudentDto dto) {
        Student student = modelMapper.map(dto, Student.class);
        student.setBirthDate(dto.getStringDateConverted(dto.getBirthDate()));
        student.setGender(GenderEnum.getClaveType(dto.getGender().toUpperCase().charAt(0)));
        return student;
    }

    private String getFullName(Student student) {
        return student.getFirstName() +
                StringUtils.SPACE +
                student.getLastName();
    }

}
