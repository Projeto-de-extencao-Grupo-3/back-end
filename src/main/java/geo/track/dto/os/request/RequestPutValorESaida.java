package geo.track.dto.os.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestPutValorESaida {
    private Integer idOrdem;
    private Double valorTotal;
    private LocalDate saidaPrevista;
    private Integer fkEntrada;


}
