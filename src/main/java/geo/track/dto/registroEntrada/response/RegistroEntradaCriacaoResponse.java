package geo.track.dto.registroEntrada.response;

import geo.track.jornada.response.listagem.CardOrdemDeServicoResponse;
import geo.track.jornada.response.entrada.RegistroEntradaResponse;
import lombok.Data;

@Data
public class RegistroEntradaCriacaoResponse {
        private RegistroEntradaResponse entrada;
        private CardOrdemDeServicoResponse ordemServico;
}
