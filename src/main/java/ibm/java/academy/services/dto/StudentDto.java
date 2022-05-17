package ibm.java.academy.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ibm.java.academy.services.constant.BusinessErrorMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Eduardo Cortés
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonPropertyOrder({"firstName", "lastName", "birthDate", "gender"})
public class StudentDto {

    @JsonProperty(value = "firstName", required = true)
    @NotEmpty(message = "First Name must not be null")
    @Size(min = 3, max = 255, message = "First Name must not be empty/blank value, and most be more than 3 characters")
    private String firstName;

    @JsonProperty(value = "lastName", required = true)
    @NotEmpty(message = "Last Name must not be null")
    @Size(min = 3, max = 255, message = "Last Name must not be empty/blank value, and most be more than 3 characters")
    private String lastName;

    @JsonProperty(value = "birthDate", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotEmpty(message = "birthDate must not be null nor empty/blank value example pattern: yyyy-MM-dd")
    @Size(max = 10, message = "birthDate most be 10 characters, example pattern: yyyy-MM-dd")
    private String birthDate;

    @JsonProperty(value = "gender", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M / F")
    @NotEmpty(message = "gender must not be null nor empty/blank value")
    @Size(max = 1, message = "gender most be one character example: M / F")
    private String gender;

    public LocalDate getStringDateConverted(String strDate) {
        try {
            return LocalDate.parse(strDate);
        } catch (DateTimeParseException ex) {
            throw new DateTimeParseException(BusinessErrorMessages.DATETIME_PARSE_INVALID, strDate, 0);
        }
    }

    public String getDateStringConverted(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }
}
