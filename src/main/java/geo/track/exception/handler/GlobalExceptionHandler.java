package geo.track.exception.handler;

import geo.track.exception.*;
import geo.track.exception.constraint.message.Domains;
import geo.track.log.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final Log log;

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionBody> DataNotFoundException(DataNotFoundException e) {
        log.error("Erro {} - ({}) {}", 404, e.getDomain(), e.getMessage());
        return ResponseEntity.status(404)
                .body(ExceptionBody
                        .builder()
                        .domain(e.getDomain())
                        .mensagem(e.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(e.getClass().getSimpleName())
                        .codigo(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionBody> BadRequestException(BadRequestException e) {
        log.error("Erro {} - ({}) {}", 400, e.getDomain(), e.getMessage());
        return ResponseEntity.status(400)
                .body(ExceptionBody
                        .builder()
                        .domain(e.getDomain())
                        .mensagem(e.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(e.getClass().getSimpleName())
                        .codigo(HttpStatus.BAD_REQUEST.value())
                        .build());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionBody> ConflictException(ConflictException e) {
        log.error("Erro {} - ({}) {}", 409, e.getDomain(), e.getMessage());
        return ResponseEntity.status(409)
                .body(ExceptionBody
                        .builder()
                        .domain(e.getDomain())
                        .mensagem(e.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(e.getClass().getSimpleName())
                        .codigo(HttpStatus.CONFLICT.value())
                        .build());
    }

    @ExceptionHandler(NotAcepptableException.class)
    public ResponseEntity<ExceptionBody> NotAcceptableException(NotAcepptableException e) {
        log.error("Erro {} - ({}) {}", 406, e.getDomain(), e.getMessage());
        return ResponseEntity.status(406)
                .body(ExceptionBody
                        .builder()
                        .domain(e.getDomain())
                        .mensagem(e.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(e.getClass().getSimpleName())
                        .codigo(HttpStatus.NOT_ACCEPTABLE.value())
                        .build());
    }

    @ExceptionHandler(BadBusinessRuleException.class)
    public ResponseEntity<ExceptionBody> BadBusinessRuleException(BadBusinessRuleException e) {
        log.error("Erro {} - ({}) {}", 422, e.getDomain(), e.getMessage());
        return ResponseEntity.status(422)
                .body(ExceptionBody
                        .builder()
                        .domain(e.getDomain())
                        .mensagem(e.getMessage())
                        .momento(LocalDateTime.now())
                        .excecao(e.getClass().getSimpleName())
                        .codigo(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String mensagem = "O corpo da requisição possui campos inválidos";
        List<InvalidField> camposInvalidos = e.getFieldErrors().stream().map(f -> {
            String rejectValue = f.getRejectedValue() == null ? "" : f.getRejectedValue().toString();

            var body = new InvalidField(f.getField(), rejectValue, f.getDefaultMessage());
            return body;
        }).toList();

        log.error("Erro {} - {} - Campos: {}", 400, mensagem, camposInvalidos);
        return ResponseEntity.status(400)
                .body(ExceptionBody
                        .builder()
                        .mensagem(mensagem)
                        .camposInvalidos(camposInvalidos)
                        .codigo(HttpStatus.BAD_REQUEST.value())
                        .momento(LocalDateTime.now())
                        .excecao(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionBody> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        String mensagem = "O corpo não pode estar vazio!";
        log.error("Erro {} - {}", 400, mensagem);
        return ResponseEntity.status(400)
                .body(ExceptionBody
                        .builder()
                        .mensagem(mensagem)
                        .codigo(HttpStatus.BAD_REQUEST.value())
                        .momento(LocalDateTime.now())
                        .excecao(e.getClass().getSimpleName())
                        .build());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ExceptionBody> UnauthorizedException(HttpClientErrorException.Unauthorized e) {
        String mensagem = "Usuário não autorizado";
        log.error("Erro {} - ({}) {}", 401, "Autenticação", mensagem);
        return ResponseEntity.status(401).body(
                ExceptionBody.builder()
                        .codigo(HttpStatus.UNAUTHORIZED.value())
                        .mensagem(mensagem)
                        .excecao(e.getClass().getSimpleName())
                        .momento(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionBody> usernameNotFoundException(UsernameNotFoundException e) {
        String mensagem = "Usuário ou senha estão incorretos";
        log.error("Erro {} - ({}) {}", 401, "Autenticação", mensagem);
        return ResponseEntity.status(401).body(
                ExceptionBody.builder()
                        .codigo(HttpStatus.UNAUTHORIZED.value())
                        .mensagem(mensagem)
                        .excecao(e.getClass().getSimpleName())
                        .momento(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ExceptionBody> usernameNotFoundException(HttpServerErrorException.InternalServerError e) {
        String mensagem = "Erro de processamento interno, chame o suporte.";
        log.error("Erro {} - ({}) {}", 401, "Autenticação", mensagem);
        return ResponseEntity.status(401).body(
                ExceptionBody.builder()
                        .codigo(HttpStatus.UNAUTHORIZED.value())
                        .mensagem(mensagem)
                        .excecao(e.getClass().getSimpleName())
                        .momento(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(AcceptedException.class)
    public ResponseEntity<ExceptionBody> acceptedException(AcceptedException e) {
        log.error("Erro {} - ({}) {}", 202, e.getDomain(), e.getMessage());
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
        log.error("Erro {} - ({}) {}", 503, e.getDomain(), e.getMessage());
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
