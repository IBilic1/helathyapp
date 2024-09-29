package hr.algebra.healthyapp.exception;

public class AppointmentNotFoundException extends RuntimeException {

    public AppointmentNotFoundException(String message, String username) {
        super(String.format(message, username));
    }
}
