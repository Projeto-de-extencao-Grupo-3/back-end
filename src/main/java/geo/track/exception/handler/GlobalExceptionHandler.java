package geo.track.exception.handler;

import geo.track.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionBody> DataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ExceptionBody
                        .builder()
                        .domain(ex.getDomain())
                        .mensagem(ex.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(ex.getClass().getSimpleName())
                        .codigo(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionBody> BadRequestException(BadRequestException ex) {
        return ResponseEntity.status(400)
                .body(ExceptionBody
                        .builder()
                        .domain(ex.getDomain())
                        .mensagem(ex.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(ex.getClass().getSimpleName())
                        .codigo(HttpStatus.BAD_REQUEST.value())
                        .build());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionBody> ConflictException(ConflictException ex) {
        return ResponseEntity.status(409)
                .body(ExceptionBody
                        .builder()
                        .domain(ex.getDomain())
                        .mensagem(ex.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(ex.getClass().getSimpleName())
                        .codigo(HttpStatus.CONFLICT.value())
                        .build());
    }

    @ExceptionHandler(NotAcepptableException.class)
    public ResponseEntity<ExceptionBody> NotAcceptableException(NotAcepptableException ex) {
        return ResponseEntity.status(406)
                .body(ExceptionBody
                        .builder()
                        .domain(ex.getDomain())
                        .mensagem(ex.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(ex.getClass().getSimpleName())
                        .codigo(HttpStatus.NOT_ACCEPTABLE.value())
                        .build());
    }

    @ExceptionHandler(BadBusinessRuleException.class)
    public ResponseEntity<ExceptionBody> BadBusinessRuleException(BadBusinessRuleException ex) {
        return ResponseEntity.status(422)
                .body(ExceptionBody
                        .builder()
                        .domain(ex.getDomain())
                        .mensagem(ex.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(ex.getClass().getSimpleName())
                        .codigo(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<InvalidField> camposInvalidos = ex.getFieldErrors().stream().map(f -> {
            String rejectValue = f.getRejectedValue() == null ? "" : f.getRejectedValue().toString();

            var body = new InvalidField(f.getField(), rejectValue, f.getDefaultMessage());
            return body;
        }).toList();
        return ResponseEntity.status(400)
                .body(ExceptionBody
                        .builder()
                        .mensagem("O corpo da requisição possui campos inválidos")
                        .camposInvalidos(camposInvalidos)
                        .codigo(HttpStatus.BAD_REQUEST.value())
                        .momento(LocalDateTime.now())
                        .excecao(ex.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionBody> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(400)
                .body(ExceptionBody
                        .builder()
                        .mensagem("O corpo não pode estar vazio!")
                        .codigo(HttpStatus.BAD_REQUEST.value())
                        .momento(LocalDateTime.now())
                        .excecao(ex.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ExceptionBody> UnauthorizedException(HttpClientErrorException.Unauthorized e) {
        return ResponseEntity.status(401).body(
                ExceptionBody.builder()
                        .codigo(HttpStatus.UNAUTHORIZED.value())
                        .mensagem("Usuário não autorizado")
                        .excecao(e.getClass().getSimpleName())
                        .momento(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionBody> usernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(401).body(
                ExceptionBody.builder()
                        .codigo(HttpStatus.UNAUTHORIZED.value())
                        .mensagem("Usuário ou senha estão incorretos")
                        .excecao(e.getClass().getSimpleName())
                        .momento(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ExceptionBody> usernameNotFoundException(HttpServerErrorException.InternalServerError e) {
        return ResponseEntity.status(401).body(
                ExceptionBody.builder()
                        .codigo(HttpStatus.UNAUTHORIZED.value())
                        .mensagem("Usuário ou senha estão incorretos")
                        .excecao(e.getClass().getSimpleName())
                        .momento(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(AcceptedException.class)
    public ResponseEntity<ExceptionBody> acceptedException(AcceptedException e) {
        return ResponseEntity.status(202).body(
                ExceptionBody.builder()
                        .codigo(HttpStatus.ACCEPTED.value())
                        .domain(e.getDomain())
                        .mensagem(e.getMessage())
                        .excecao(e.getClass().getSimpleName())
                        .momento(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ExceptionBody> serviceUnavailableException(ServiceUnavailableException e) {
        return ResponseEntity.status(503).body(
                ExceptionBody.builder()
                        .codigo(HttpStatus.SERVICE_UNAVAILABLE.value())
                        .domain(e.getDomain())
                        .mensagem(e.getMessage())
                        .excecao(e.getClass().getSimpleName())
                        .momento(LocalDateTime.now())
                        .build()
        );
    }
}
