package ibm.java.academy.services.eduardocortes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.time.format.DateTimeParseException;

/**
 * @author Eduardo Cortés
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        log.error("User Not Found Exception: {}", exception.getMessage());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorDetails errorDetails = new ErrorDetails(
                timestamp.toLocalDateTime().toString(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        log.error("Illegal Argument Exception: {}", exception.getMessage());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorDetails errorDetails = new ErrorDetails(
                timestamp.toLocalDateTime().toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateTimeParseException(DateTimeParseException exception, WebRequest request) {
        log.error("DateTime Parse Exception: {}", exception.getMessage());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorDetails errorDetails = new ErrorDetails(
                timestamp.toLocalDateTime().toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request) {
        log.error("Exception: {}", exception.getMessage());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ErrorDetails errorDetails = new ErrorDetails(
                timestamp.toLocalDateTime().toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
