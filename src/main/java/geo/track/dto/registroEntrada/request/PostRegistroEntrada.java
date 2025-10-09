package geo.track.dto.registroEntrada.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PostRegistroEntrada {
    private LocalDate dtEntradaPrevista;
    private Integer fkVeiculo;
}
