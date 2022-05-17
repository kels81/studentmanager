package ibm.java.academy.services.eduardocortes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * @author Eduardo Cortés
 */

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDataResponse {

    @JsonProperty("id")
    private Long studentId;

    @JsonProperty("fullName")
    private String fullname;

}
