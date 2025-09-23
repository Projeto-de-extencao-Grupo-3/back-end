package geo.track.exception.handler;

import geo.track.exception.*;
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

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionBody> ConflictException(ConflictException ex){
        return new ResponseEntity<>(new ExceptionBody(ex.getDomain(), ex.getMessage(), LocalDateTime.now(), ConflictException.class.getSimpleName(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotAcepptableException.class)
    public ResponseEntity<ExceptionBody> NotAcceptableException(NotAcepptableException ex){
        return new ResponseEntity<>(new ExceptionBody(ex.getDomain(), ex.getMessage(), LocalDateTime.now(), NotAcepptableException.class.getSimpleName(), HttpStatus.NOT_ACCEPTABLE.value()), HttpStatus.NOT_ACCEPTABLE);
    }
}
