package hr.algebra.healthyapp.exception;

public class AppointmentExistsException  extends RuntimeException {

    public AppointmentExistsException(String message) {
        super(message);
    }
}
