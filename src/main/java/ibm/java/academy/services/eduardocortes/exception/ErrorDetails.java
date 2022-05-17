package ibm.java.academy.services.eduardocortes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Eduardo Cortés
 */

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String details;

}
