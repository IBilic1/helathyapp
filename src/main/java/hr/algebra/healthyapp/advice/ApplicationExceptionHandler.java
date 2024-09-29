package hr.algebra.healthyapp.advice;

import hr.algebra.healthyapp.exception.AppointmentExistsException;
import hr.algebra.healthyapp.exception.AppointmentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    public static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleStudentNotFoundException(Exception exception) {
        LOG.error(exception.getMessage(), exception);
        return ResponseEntity
                .internalServerError()
                .body(exception.getMessage());
    }

    @ExceptionHandler({IncorrectResultSizeDataAccessException.class})
    public ResponseEntity<Void> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException exception) {
        LOG.error(exception.getMessage(), exception);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({AppointmentNotFoundException.class})
    public ResponseEntity<String> handleEntityDoesNotExistsException(AppointmentNotFoundException entityDoesNotExists) {
        LOG.error(entityDoesNotExists.getMessage(), entityDoesNotExists);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({AppointmentExistsException.class})
    public ResponseEntity<String> handleAppointmentExistsException(AppointmentExistsException appointmentExistsException) {
        LOG.error(appointmentExistsException.getMessage(), appointmentExistsException);
        return ResponseEntity.badRequest().body(appointmentExistsException.getMessage());
    }
}
