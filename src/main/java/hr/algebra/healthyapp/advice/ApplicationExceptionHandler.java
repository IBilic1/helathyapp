package hr.algebra.healthyapp.advice;

import hr.algebra.healthyapp.exception.EntityDoesNotExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
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
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler({IncorrectResultSizeDataAccessException.class})
    public ResponseEntity<Void> handleIncorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException exception) {
        LOG.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler({EntityDoesNotExistsException.class})
    public ResponseEntity<String> handleEntityDoesNotExistsException(EntityDoesNotExistsException entityDoesNotExists) {
        LOG.error(entityDoesNotExists.getMessage(), entityDoesNotExists);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(entityDoesNotExists.getMessage());
    }
}
