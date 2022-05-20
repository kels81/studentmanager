package ibm.java.academy.services.eduardocortes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eduardo Cortés
 */

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerInvalidRequest(MethodArgumentNotValidException exception, WebRequest request) {
        log.error("Method Argument Not Valid Exception: {}", exception.getMessage());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Map<String, String> errorMap = new HashMap<>();
        exception
                .getBindingResult()
                .getFieldErrors().forEach( error -> {
                    errorMap.put(error.getField(), error.getDefaultMessage());
                });

        ErrorDetails errorDetails = new ErrorDetails(
                timestamp.toLocalDateTime().toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMap,
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
