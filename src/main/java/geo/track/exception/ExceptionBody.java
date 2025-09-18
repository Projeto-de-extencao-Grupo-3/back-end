package geo.track.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionBody {
    private String domain;
    private String mensagem;
    private LocalDateTime momento;
    private String excessao;
    private Integer codigo;
}
