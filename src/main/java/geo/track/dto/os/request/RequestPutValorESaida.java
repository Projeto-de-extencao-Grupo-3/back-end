package geo.track.dto.os.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class RequestPutValorESaida {
    private Integer idOrdem;
    private Double valorTotal;
    private LocalDate saidaPrevista;
    private Integer fkEntrada;


}
