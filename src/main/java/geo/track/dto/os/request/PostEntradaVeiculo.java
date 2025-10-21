package geo.track.dto.os.request;

import geo.track.domain.OrdemDeServicos;
import geo.track.domain.Veiculos;
import geo.track.enums.os.StatusVeiculo;
import lombok.Data;

@Data
public class PostEntradaVeiculo {
    private StatusVeiculo status;
    private Double valorTotal = 0.0;
    private OrdemDeServicos fkEntrada;
}
