package ibm.java.academy.services.controller;

import ibm.java.academy.services.dto.GetDataResponse;
import ibm.java.academy.services.dto.GetStudentIdResponse;
import ibm.java.academy.services.dto.GetStudentsDataResponse;
import ibm.java.academy.services.dto.StudentDto;
import ibm.java.academy.services.entity.Student;
import ibm.java.academy.services.entity.enums.GenderEnum;
import ibm.java.academy.services.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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


    @GetMapping("/all")
    public ResponseEntity<GetStudentsDataResponse> getAll() {
        List<StudentDto> lstDto = studentService.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity(new GetStudentsDataResponse(lstDto), HttpStatus.OK);
    }

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

    private StudentDto convertToDto(Student student) {
        StudentDto dto = modelMapper.map(student, StudentDto.class);
        dto.setBirthDate(dto.getDateStringConverted(student.getBirthDate()));
        return dto;
    }

    private String getFullName(Student student) {
        String sb = student.getFirstName() +
                StringUtils.SPACE +
                student.getLastName();
        return sb;
    }

}
