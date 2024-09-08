package hr.algebra.healthyapp.exception;

public class EntityDoesNotExistsException extends RuntimeException {

    public EntityDoesNotExistsException(String message, String username) {
        super(String.format(message, username));
    }
}
