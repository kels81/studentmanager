package ibm.java.academy.services.eduardocortes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ibm.java.academy.services.eduardocortes.constant.BusinessErrorMessages;
import ibm.java.academy.services.eduardocortes.dto.StudentDto;
import ibm.java.academy.services.eduardocortes.entity.Student;
import ibm.java.academy.services.eduardocortes.entity.enums.GenderEnum;
import ibm.java.academy.services.eduardocortes.exception.ResourceNotFoundException;
import ibm.java.academy.services.eduardocortes.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Eduardo Cortés
 */

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @InjectMocks
    private StudentController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private StudentService studentService;

    private static final String URL_POST_ADD_STUDENT = "/academy/student";
    private static final String URL_GET_ID_STUDENT = "/academy/student/{id}";

    private static final Long INVALID_ID = 10L;

    private static final Long ID_M = 1L;
    private static final String FIRST_NAME_M = "Alfredo";
    private static final String LAST_NAME_M = "Rojas";
    private static final LocalDate BIRT_DATE_M = LocalDate.of(1995, 11, 6);
    private static final GenderEnum GENDER_M = GenderEnum.MALE;

    private static final Long ID_F = 2L;
    private static final String FIRST_NAME_F = "Laura";
    private static final String LAST_NAME_F = "Zapata";
    private static final LocalDate BIRT_DATE_F = LocalDate.of(1994, 1, 26);
    private static final GenderEnum GENDER_F = GenderEnum.FEMALE;

    private static final String FIRST_NAME_DTO_INVALID = "So";
    private static final String LAST_NAME_DTO_INVALID = " ";
    private static final String BIRT_DATE_DTO_INVALID = "07-04-199";
    private static final String GENDER_DTO_INVALID = "s";

    private static final String FIRST_NAME_DTO_VALID = "Soraya";
    private static final String LAST_NAME_DTO_VALID = "Jimenez ";
    private static final String BIRT_DATE_DTO_VALID = "1972-07-11";
    private static final String GENDER_DTO_VALID = "F";


    @Test
    void getByIdWhenSuccess() throws Exception {
        Student student = getStudent(ID_M, FIRST_NAME_M, LAST_NAME_M, BIRT_DATE_M, GENDER_M.getKey());
        when(studentService.findById(ID_M)).thenReturn(student);

        mockMvc.perform(
                        get(URL_GET_ID_STUDENT, ID_M)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_M))
                .andExpect(jsonPath("$.fullName").value(FIRST_NAME_M.concat(StringUtils.SPACE).concat(LAST_NAME_M)));
    }

    @Test
    void getByIdWhenIsNotSuccess() throws Exception {
        Exception exception = new ResourceNotFoundException(BusinessErrorMessages.STUDENT_ID_INVALID + INVALID_ID);
        when(studentService.findById(INVALID_ID)).thenThrow(exception);

        mockMvc.perform(
                        get(URL_GET_ID_STUDENT, INVALID_ID)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value(exception.getMessage()));
    }

    @Test
    void addWhenCreatedSuccess() throws Exception {
        StudentDto dto = getStudentDto(FIRST_NAME_DTO_VALID, LAST_NAME_DTO_VALID, BIRT_DATE_DTO_VALID, GENDER_DTO_VALID);
        Student student = getStudent(ID_F, FIRST_NAME_F, LAST_NAME_F, BIRT_DATE_F, GENDER_F.getKey());
        when(studentService.findStudent(convertToEntity(dto))).thenReturn(Boolean.FALSE);
        when(studentService.add(convertToEntity(dto))).thenReturn(student);

        mockMvc.perform(
                        post(URL_POST_ADD_STUDENT)
                                .content(asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(ID_F));
    }

    @Test
    void addWhenCreatedFail() throws Exception {
        StudentDto dto = getStudentDto(FIRST_NAME_DTO_VALID, LAST_NAME_DTO_VALID, BIRT_DATE_DTO_VALID, GENDER_DTO_VALID);
        when(studentService.findStudent(convertToEntity(dto))).thenReturn(Boolean.TRUE);

        mockMvc.perform(
                        post(URL_POST_ADD_STUDENT)
                                .content(asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void addWhenValidationDtoFirstNameAndLastNameInvalid() throws Exception {
        StudentDto dto = getStudentDto(FIRST_NAME_DTO_INVALID, LAST_NAME_DTO_INVALID, BIRT_DATE_DTO_VALID, GENDER_DTO_VALID);
        mockMvc.perform(
                        post(URL_POST_ADD_STUDENT)
                                .content(asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addWhenValidationDtoBirtDateInvalid() throws Exception {
        StudentDto dto = getStudentDto(FIRST_NAME_DTO_VALID, LAST_NAME_DTO_VALID, BIRT_DATE_DTO_INVALID, GENDER_DTO_VALID);
        mockMvc.perform(
                        post(URL_POST_ADD_STUDENT)
                                .content(asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(BusinessErrorMessages.DATETIME_PARSE_INVALID));
    }

    @Test
    void addWhenValidationDtoGenderInvalid() throws Exception {
        StudentDto dto = getStudentDto(FIRST_NAME_DTO_VALID, LAST_NAME_DTO_VALID, BIRT_DATE_DTO_VALID, GENDER_DTO_INVALID);
        mockMvc.perform(
                        post(URL_POST_ADD_STUDENT)
                                .content(asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value(BusinessErrorMessages.GENDER_KEY_INVALID.concat(GENDER_DTO_INVALID.toUpperCase())));
    }


    private Student getStudent(Long id, String firstName, String lastName, LocalDate birthDate, char gender) {
        return new Student(id, firstName, lastName, birthDate, gender);
    }

    private StudentDto getStudentDto(String firstName, String lastName, String birthDate, String gender) {
        return new StudentDto(firstName, lastName, birthDate, gender);
    }

    private Student convertToEntity(StudentDto dto) {
        Student student = modelMapper.map(dto, Student.class);
        student.setBirthDate(dto.getStringDateConverted(dto.getBirthDate()));
        student.setGender(GenderEnum.getClaveType(dto.getGender().toUpperCase().charAt(0)));
        return student;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}