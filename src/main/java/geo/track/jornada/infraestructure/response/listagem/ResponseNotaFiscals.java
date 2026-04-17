package geo.track.jornada.infraestructure.response.listagem;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseNotaFiscals(
        Long quantidadeNotasFiscaisPendentes,
        List<CardOrdemDeServicoResponse> ordensDeServicos
        ){
}
