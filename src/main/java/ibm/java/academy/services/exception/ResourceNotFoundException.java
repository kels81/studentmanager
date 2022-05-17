package ibm.java.academy.services.exception;

/**
 * @author Eduardo Cortés
 */

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
