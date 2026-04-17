package geo.track.jornada.infraestructure.response.entrada;

import geo.track.jornada.infraestructure.response.listagem.CardOrdemDeServicoResponse;
import lombok.Data;

@Data
public class RegistroEntradaCriacaoResponse {
        private RegistroEntradaResponse entrada;
        private CardOrdemDeServicoResponse ordemServico;
}
