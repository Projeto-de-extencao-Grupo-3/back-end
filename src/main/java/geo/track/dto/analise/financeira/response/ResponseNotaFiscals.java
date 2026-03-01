package geo.track.dto.analise.financeira.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import geo.track.dto.os.response.CardOrdemDeServicoResponse;
import geo.track.dto.os.response.OrdemDeServicoResponse;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseNotaFiscals(
        Long quantidadeNotasFiscaisPendentes,
        List<CardOrdemDeServicoResponse> ordensDeServicos
        ){
}
