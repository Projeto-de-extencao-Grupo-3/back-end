package geo.track.dto.os.request;

import geo.track.enums.os.StatusVeiculo;
import lombok.Data;

@Data
public class PostEntradaVeiculo {
    private StatusVeiculo status;
    private Double valorTotal = 0.0;
    private Integer fkEntrada;
}
