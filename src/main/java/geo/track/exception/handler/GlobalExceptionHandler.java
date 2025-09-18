package geo.track.exception.handler;

import geo.track.exception.ExceptionBody;
import geo.track.exception.BadRequestException;
import geo.track.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionBody> DataNotFoundException(DataNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionBody(ex.getDomain(),ex.getMessage(), LocalDateTime.now(), DataNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionBody> BadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new ExceptionBody(ex.getDomain(), ex.getMessage(), LocalDateTime.now(), BadRequestException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
