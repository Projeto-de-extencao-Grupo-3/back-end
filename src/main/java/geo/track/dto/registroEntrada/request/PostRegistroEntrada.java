package geo.track.dto.registroEntrada.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class PostRegistroEntrada {
    private LocalDate dtEntradaPrevista;
    private Integer fkVeiculo;
}
