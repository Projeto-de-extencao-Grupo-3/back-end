package geo.track.dto.registroEntrada.response;

import geo.track.dto.os.response.CardOrdemDeServicoResponse;
import geo.track.enums.os.StatusVeiculo;
import geo.track.service.OrdemDeServicoService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
public class RegistroEntradaCriacaoResponse {
        private RegistroEntradaResponse entrada;
        private CardOrdemDeServicoResponse ordemServico;
}
