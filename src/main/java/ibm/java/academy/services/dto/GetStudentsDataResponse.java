package ibm.java.academy.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author Eduardo Cortés
 */

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudentsDataResponse {

    @JsonProperty("students")
    private List<StudentDto> data;

}
